package com.hoop.api.request.game;

import com.hoop.api.domain.Game;
import lombok.Builder;
import lombok.Data;

@Data
public class GameAttend {
    private Game game; //복합키 ID
    private Boolean isBallFlag;
    private Boolean isHost = false;
    @Builder
    public GameAttend(Game game, Boolean isHost, Boolean isBallFlag) {
        this.game = game;
        this.isHost = isHost;
        this.isBallFlag = isBallFlag;
    }
}