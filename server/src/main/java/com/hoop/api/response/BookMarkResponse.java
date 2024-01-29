//package com.hoop.api.response;
//
//import com.hoop.api.domain.BookMark;
//import com.hoop.api.domain.Game;
//import com.hoop.api.domain.User;
//import jakarta.persistence.*;
//import lombok.*;
//
//import static jakarta.persistence.GenerationType.IDENTITY;
//
//@Data
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//public class BookMarkResponse {
//    private Long id;
//    private Long gameId;
//    private String gameTitle;
//
//    public BookMarkResponse(Long id, String gameTitle, Long gameId) {
//        this.id = id;
//        this.gameTitle = gameTitle;
//        this.gameId = gameId;
//    }
//
//    public BookMarkResponse(BookMark bookMark) {
//        this.id = bookMark.getId();
//        this.gameTitle = bookMark.getGame().getTitle();
//        this.gameId = bookMark.getGame().getId();
//    }
//}
