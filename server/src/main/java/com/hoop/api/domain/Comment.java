package com.hoop.api.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "id")
public class Comment {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn
    private User user;

    @Column(nullable = false)
    private String content;

    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn
    private Game game;

    @Builder
    public Comment(User user, String content) {
        this.createdAt = LocalDateTime.now();
        this.user = user;
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
