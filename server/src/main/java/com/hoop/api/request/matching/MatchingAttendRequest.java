package com.hoop.api.request.matching;

import com.hoop.api.domain.Matching;
import com.hoop.api.domain.User;
import lombok.Builder;
import lombok.Data;

@Data
public class MatchingAttendRequest {
    private Matching matching; //복합키 ID
    private Boolean isBallFlag;
    private Boolean isHost = false;

    @Builder
    public MatchingAttendRequest(Matching matching, Boolean isHost, Boolean isBallFlag) {
        this.matching = matching;
        this.isHost = isHost;
        this.isBallFlag = isBallFlag;
    }

}








