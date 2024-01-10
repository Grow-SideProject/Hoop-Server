package com.hoop.api.request.user;

import com.hoop.api.domain.Game;
import com.hoop.api.domain.User;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FeedbackRequest {


    private Long targetId;
    private Long gameId;
    private String content;
    private Integer score;

    @Builder
    public FeedbackRequest(Long targetId, Long gameId, String content, Integer score) {
        this.targetId = targetId;
        this.gameId = gameId;
        this.content = content;
        this.score = score;
    }
}
