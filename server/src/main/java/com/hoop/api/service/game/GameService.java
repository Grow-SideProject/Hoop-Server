package com.hoop.api.service.game;

import com.hoop.api.constant.AttendantStatus;
import com.hoop.api.domain.Attendant;
import com.hoop.api.domain.Comment;
import com.hoop.api.domain.Game;

import com.hoop.api.domain.User;
import com.hoop.api.exception.*;
import com.hoop.api.repository.attendant.AttendantRepository;
import com.hoop.api.repository.game.GameRepository;
import com.hoop.api.repository.UserRepository;
import com.hoop.api.request.game.GameCreate;
import com.hoop.api.request.game.GameEdit;
import com.hoop.api.request.game.GameSearch;
import com.hoop.api.response.game.AttendantResponse;
import com.hoop.api.response.game.CommentResponse;
import com.hoop.api.response.game.GameDetailResponse;
import com.hoop.api.response.game.GameListResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

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
        gameRepository.getMyGameAfterNowAndIsOpened(userId).ifPresent(dup -> {
            throw new AlreadyExistsGameAttendException();
        });
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
        Attendant host = game.getAttendants().stream().filter(Attendant::getIsHost).findFirst().get();
        if (!host.getUser().getId().equals(userId)) {
            throw new RuntimeException("게임장만 수정할 수 있습니다.");
        }
        game.GameEdit(gameEdit);
        gameRepository.save(game);
    }

    public Page<GameListResponse> getList(GameSearch gameSearch) {
        List<GameListResponse> gameList = gameRepository.getList(gameSearch).stream()
                .map(GameListResponse::new)
                .collect(Collectors.toList());
        return new PageImpl<>(gameList, PageRequest.of(gameSearch.getPage(), gameSearch.getSize()), gameRepository.count());
    }

    @Transactional
    public GameDetailResponse getById(Long userId, Long gameId) {
        Game game = gameRepository.findById(gameId).orElseThrow(GameNotFound::new);
        Integer views = game.getViews() + 1;
        game.setViews(views);
        gameRepository.save(game);

        // 게임 Response 생성
        GameDetailResponse gameDetailResponse = new GameDetailResponse(game);
        // 참여자 Response 생성
        List<AttendantResponse> gameAttendantResponseList
                = game.getAttendants().stream().filter(attendant -> attendant.getStatus().equals(AttendantStatus.APPROVE))
                .map(AttendantResponse::new).toList();


        if (gameAttendantResponseList.size() < game.getMaxAttend()) {
            // TODO : ISCLOSED
        } else if (gameAttendantResponseList.isEmpty()) {
            // TODO : ISCANCELED
        }
        Long hostId = game.getAttendants().stream().filter(Attendant::getIsHost).findFirst()
                .orElseThrow(GameNotFound::new)
                .getUser().getId();
        // 댓글 Response 생성
        List<CommentResponse> commentResponseList = converseCommentResponse(userId, hostId, game.getComments());
        Boolean isHost = userId.equals(hostId);
        gameDetailResponse.setGameAttendantResponseList(gameAttendantResponseList);
        gameDetailResponse.setCommentResponseList(commentResponseList);
        gameDetailResponse.setIsHost(isHost);
        /*
        // BookMark 기능 임시 주석 처리
        Boolean isBookmarked = game.getBookMarks().stream().anyMatch(bookMark -> bookMark.getUser().getId().equals(userId));
        Integer bookmarkCount = game.getBookMarks().size();
        gameDetailResponse.setIsBookmarked(isBookmarked);
        gameDetailResponse.setBookmarkCount(bookmarkCount);
        */
        return gameDetailResponse;

    }

    private List<CommentResponse> converseCommentResponse(Long userId, Long hostId, List<Comment> comments){
        List<CommentResponse> commentResponseList = new ArrayList<>();
        comments.stream().map(CommentResponse::new).forEach(comment -> {
            comment.setIsMine(comment.getUserId().equals(userId));
            comment.setIsHost(comment.getUserId().equals(hostId));
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
        return commentResponseList;
    }


    public AttendantResponse attendGame(Long userId, Long gameId) {
        // 중복참여시 예외처리
        gameRepository.getMyGameAfterNowAndIsOpened(userId).ifPresent(dup -> {
            throw new AlreadyExistsGameAttendException();
        });
        User user = userRepository.findById(userId).orElseThrow(UserNotFound::new);
        Game game = gameRepository.findById(gameId).orElseThrow(GameNotFound::new);
        // 모집인원 초과시 예외처리
        int attendCount = game.getAttendants().stream().filter(gameAttendant -> gameAttendant.getStatus().equals(AttendantStatus.APPROVE)).toList().size();
        if (attendCount >= game.getMaxAttend()) {
            throw new RecruitmentClosedException();
        }
        Attendant attendant = Attendant
                .builder()
                .user(user)
                .game(game)
                .status(AttendantStatus.DEFAULT)
                .isHost(false)
                .build();
        attendantRepository.save(attendant);
        return new AttendantResponse(attendant);
    }

    @Transactional
    public AttendantResponse exitGame(Long userId, Long gameId) {
        //게임에 참여중이 아님
        Attendant gameAttendant = attendantRepository.findByUserIdAndGameId(userId, gameId)
                .orElseThrow(GameNotFound::new);
        if (gameAttendant.getStatus().equals(AttendantStatus.EXIT)) throw new AttendantStatusException();
        Game game = gameRepository.findById(gameId).orElseThrow(GameNotFound::new);
        boolean ishostChanged = false;
        for (Attendant attendant : game.getAttendants()) {
            if (!attendant.getIsHost() && attendant.getStatus().equals(AttendantStatus.APPROVE) ) {
                ishostChanged = true;
                attendant.setHost(true);
                attendantRepository.save(attendant);
                break;
            }
        }
        if (ishostChanged) gameAttendant.setHost(false); // 호스트 변경에 성공했으면 기존 호스트를 false로 변경
        gameAttendant.setAttend(AttendantStatus.EXIT);
        attendantRepository.save(gameAttendant);
        return new AttendantResponse(gameAttendant);
    }


}
