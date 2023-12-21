package com.hoop.api.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nickName;

    @Column(nullable = false)
    private String content;


    @ManyToOne
    @JoinColumn
    private Game game;

    @Builder
    public Comment(String nickName, String content) {
        this.nickName = nickName;
        this.content = content;
    }

    public void setGame(Game game) {
        this.game = game;
        this.game.getComments().add(this);
    }

    public void removeGame(Game game) {
        this.game = game;
        this.game.getComments().remove(this);
    }
}
