    package com.hoop.api.domain;

    import com.fasterxml.jackson.annotation.JsonIdentityInfo;
    import com.fasterxml.jackson.annotation.ObjectIdGenerators;
    import jakarta.persistence.*;
    import jakarta.validation.constraints.NotNull;
    import lombok.*;

    import java.time.LocalDateTime;
    import java.util.ArrayList;
    import java.util.List;

    import static jakarta.persistence.GenerationType.IDENTITY;

    @Data
    @Entity
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Table(
            indexes = {
                    @Index(name = "IDX_COMMENT_PARENT_ID", columnList = "parent_id")
            }
    )
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
        @JoinColumn(name = "game_id")
        private Game game;

        @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
        @JoinColumn(name = "parent_id")
        private Comment parent;

        @Builder
        public Comment(User user, String content, Comment parent) {
            this.createdAt = LocalDateTime.now();
            this.user = user;
            this.content = content;
            this.parent = parent;
        }
        public void setGame(Game game) {
            this.game = game;
            this.game.getComments().add(this);
        }

    }