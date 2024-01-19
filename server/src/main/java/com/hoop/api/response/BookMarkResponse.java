package com.hoop.api.response;

import com.hoop.api.domain.BookMark;
import com.hoop.api.domain.Game;
import com.hoop.api.domain.User;
import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.GenerationType.IDENTITY;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookMarkResponse {
    private Long id;
    private User user;
    private Game game;
    @Builder
    public BookMarkResponse(User user, Game game) {
        this.user = user;
        this.game = game;
    }

    public BookMarkResponse(BookMark bookMark) {
        this.id = bookMark.getId();
        this.user = bookMark.getUser();
        this.game = bookMark.getGame();
    }
}
