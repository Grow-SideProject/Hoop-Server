package com.hoop.api.response;

import com.hoop.api.constant.AttendantStatus;
import com.hoop.api.domain.Attendant;
import com.hoop.api.domain.User;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 연관관계 클래스
 */
@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class GameAttendantResponse {
    private Long id;
    private Boolean isHost;
    private Long userId;
    private String userNickname;
    private String profileImagePath;

    public GameAttendantResponse(Attendant attendant) {
        this.id = attendant.getId();
        this.isHost = attendant.getIsHost();
        this.userNickname = attendant.getUser().getNickName();
        this.userId = attendant.getUser().getId();
        this.profileImagePath = attendant.getUser().getProfileImagePath();
    }
    @Builder
    public GameAttendantResponse(Long id, Boolean isHost, AttendantStatus status, User user){
        this.id = id;
        this.isHost = isHost;
        this.userNickname = user.getNickName();
        this.userId = user.getId();
        this.profileImagePath = getProfileImagePath();
    }

}