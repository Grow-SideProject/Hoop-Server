package com.hoop.api.service.game;

import com.hoop.api.constant.AttendantStatus;
import com.hoop.api.domain.Attendant;
import com.hoop.api.domain.Game;

import com.hoop.api.domain.User;
import com.hoop.api.exception.AlreadyExistsGameAttendException;
import com.hoop.api.exception.GameNotFound;
import com.hoop.api.exception.UserNotFound;
import com.hoop.api.repository.game.AttendantRepository;
import com.hoop.api.repository.game.GameRepository;
import com.hoop.api.repository.UserRepository;
import com.hoop.api.request.game.GameCreate;
import com.hoop.api.request.game.GameEdit;
import com.hoop.api.request.game.GameSearch;
import com.hoop.api.response.CommentResponse;
import com.hoop.api.response.GameAttendantResponse;
import com.hoop.api.response.GameDetailResponse;
import com.hoop.api.response.GameListResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameRepository gameRepository;
    private final AttendantRepository attendantRepository;
    private final UserRepository userRepository;


    @Transactional
    public void create(Long userId, GameCreate gameCreate) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFound::new);
        Game game = gameCreate.toGame();
        Attendant attendant = Attendant
                .builder()
                .game(game)
                .user(user)
                .isHost(true)
                .status(AttendantStatus.APPROVE)
                .isBallFlag(gameCreate.getIsBallFlag())
                .build();
        gameRepository.save(game);
        attendantRepository.save(attendant);
    }

    @Transactional
    public void edit(Long userId, GameEdit gameEdit) {
        Game game = gameRepository.findById(gameEdit.getGameId())
                .orElseThrow(GameNotFound::new);
        game.GameEdit(gameEdit);
        gameRepository.save(game);
    }
    public GameDetailResponse get(Long userId, Long gameId) {
        Game game = gameRepository.findById(gameId).orElseThrow(GameNotFound::new);
        Integer views = game.getViews() +1;
        game.setViews(views);
        gameRepository.save(game);

        Boolean isHost = game.getAttendants().stream().anyMatch(attendant -> (attendant.getIsHost()) && (attendant.getUser().getId().equals(userId)));

        // 참여자 Response 생성
        List<GameAttendantResponse> gameAttendantResponseList
                = game.getAttendants().stream().filter(attendant -> attendant.getStatus().equals(AttendantStatus.APPROVE))
                .map(GameAttendantResponse::new).toList();

        // 댓글 Response 생성
        List<CommentResponse> commentResponseList = new ArrayList<>();
        game.getComments().stream().map(CommentResponse::new).forEach(comment -> {
            comment.setIsMine(comment.getUserId().equals(userId));
            if (comment.getParentId() == null) {
                commentResponseList.add(comment);
            } else {
                for (CommentResponse commentResponse : commentResponseList) {
                    if (commentResponse.getId().equals(comment.getParentId())) {
                        commentResponse.getChildren().add(comment);
                    }
                }
            }
        });
        return GameDetailResponse.builder()
                .id(game.getId())
                .title(game.getTitle())
                .content(game.getContent())
                .courtName(game.getCourtName())
                .address(game.getAddress())
                .xLoc(game.getXLoc())
                .yLoc(game.getYLoc())
                .gameCategory(game.getGameCategory())
                .startTime(game.getStartTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .duration(game.getDuration())
                .gender(game.getGender())
                .maxAttend(game.getMaxAttend())
                .isBallFlag(game.getIsBallFlag())
                .createdAt(game.getCreatedAt())
                .levels(game.getLevels())
                .comments(commentResponseList)
                .attendants(gameAttendantResponseList)
                .isHost(isHost)
                .views(views)
                .bookmarkCount(game.getBookMarks().size())
                .build();
    }

    public Page<GameListResponse> getList(GameSearch gameSearch) {
        List<GameListResponse> gameList = gameRepository.getList(gameSearch).stream()
                .map(GameListResponse::new)
                .collect(Collectors.toList());
        return new PageImpl<>(gameList, PageRequest.of(gameSearch.getPage(), gameSearch.getSize()), gameRepository.count());
    }


    public GameAttendantResponse attendGame(Long userId, Long gameId, boolean ballFlag) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFound::new);
        Game game = gameRepository.findById(gameId).orElseThrow(GameNotFound::new);
        attendantRepository.findByUserAndGame(user, game)
                .ifPresent(attendant -> {
                    throw new AlreadyExistsGameAttendException();
        });
        Attendant attendant = Attendant
                .builder()
                .user(user)
                .game(game)
                .status(AttendantStatus.DEFAULT)
                .isHost(false)
                .isBallFlag(ballFlag)
                .build();
        attendantRepository.save(attendant);
        return new GameAttendantResponse(attendant);
    }

    @Transactional
    public GameAttendantResponse exitGame(Long userId, Long gameId) {
        Game game = gameRepository.findById(gameId).orElseThrow(GameNotFound::new);
        Attendant gameAttendant = attendantRepository.findByUserIdAndGameId(userId, gameId)
                    .orElseThrow(GameNotFound::new);
        for (Attendant attendant : game.getAttendants()) {
            if (!attendant.getIsHost() && attendant.getStatus().equals(AttendantStatus.APPROVE) ) {
                attendant.setHost(true);
                attendantRepository.save(attendant);
                break;
            }
        }
        gameAttendant.setHost(false);
        gameAttendant.setAttend(AttendantStatus.EXIT);
        attendantRepository.save(gameAttendant);
        return new GameAttendantResponse(gameAttendant);
    }


}
