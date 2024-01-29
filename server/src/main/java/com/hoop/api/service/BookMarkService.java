//package com.hoop.api.service;
//
//import com.hoop.api.domain.BookMark;
//import com.hoop.api.domain.Game;
//import com.hoop.api.domain.User;
//import com.hoop.api.repository.BookMarkRepository;
//import com.hoop.api.repository.UserRepository;
//import com.hoop.api.repository.game.GameRepository;
//import com.hoop.api.response.BookMarkResponse;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import com.hoop.api.exception.*;
//
//@Service
//@RequiredArgsConstructor
//public class BookMarkService {
//    private final BookMarkRepository bookMarkRepository;
//    private final UserRepository userRepository;
//    private final GameRepository gameRepository;
//
//    public List<BookMarkResponse> getList(Long userId) {
//        return bookMarkRepository.findByUserId(userId)
//                .stream()
//                .map(BookMarkResponse::new)
//                .toList();
//    }
//
//    public void addBookMark(Long userId, Long gameId) {
//        User user = userRepository.findById(userId).orElseThrow(UserNotFound::new);
//        Game game = gameRepository.findById(gameId).orElseThrow(GameNotFound::new);
//        BookMark bookMark = BookMark.builder()
//                .user(user)
//                .game(game)
//                .build();
//        bookMarkRepository.save(bookMark);
//    }
//    public void delBookMark(Long userId, Long gameId) {
//        BookMark bookMark = bookMarkRepository.findByUserIdAndGameId(userId, gameId).orElseThrow(BookMarkNotFound::new);
//        bookMarkRepository.delete(bookMark);
//    }
//}
