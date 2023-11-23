package com.hoop.api.request.game;

import com.hoop.api.domain.Game;
import lombok.Builder;
import lombok.Data;

@Data
public class GameAttend {
    private Long gameId; //복합키 ID
    private Boolean isBallFlag;

    @Builder
    public GameAttend(Long gameId, Boolean isBallFlag) {
        this.gameId = gameId;
        this.isBallFlag = isBallFlag;
    }



}