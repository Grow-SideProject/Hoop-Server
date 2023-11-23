package com.hoop.api.request.user;

import com.hoop.api.domain.Game;
import com.hoop.api.domain.User;
import lombok.Data;

@Data
public class FeedbackRequest {


    private Long targetId;
    private Long gameId;
    private String content;
    private Integer score;

    public FeedbackRequest(Long targetId, Long gameId, String content, Integer score) {
        this.targetId = targetId;
        this.gameId = gameId;
        this.content = content;
        this.score = score;
    }
}
