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
public class Feedback {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private int score;

    private String content;

    @OneToOne
    private User user;

    @OneToOne
    private User target;

    @ManyToOne
    private Game game;

    @Builder
    public Feedback(User user, User target, Game game, String content, int score) {
        this.user = user;
        this.target = target;
        this.game = game;
        this.content = content;
        this.score = score;
    }
}
