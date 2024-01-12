package com.hoop.api.response;

import com.hoop.api.constant.AttendantStatus;
import com.hoop.api.domain.Attendant;
import com.hoop.api.domain.User;
import com.hoop.api.service.ImageService;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 연관관계 클래스
 */
@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class AttendantResponse {
    private Long id;
    private Boolean isBallFlag;
    private Boolean isHost;
    private AttendantStatus status;
    private LocalDateTime createdAt;
    private Long userId;
    private String userNickname;
    private String profileImagePath;

    public AttendantResponse(Attendant attendant) {
        this.id = attendant.getId();
        this.isBallFlag = attendant.getIsBallFlag();
        this.createdAt = attendant.getCreatedAt();
        this.status = attendant.getStatus();
        this.isHost = attendant.getIsHost();
        this.userNickname = attendant.getUser().getNickName();
        this.userId = attendant.getUser().getId();
        this.profileImagePath = attendant.getUser().getProfileImagePath();
    }
    @Builder
    public AttendantResponse(Boolean isHost, AttendantStatus status, User user){
        this.createdAt = LocalDateTime.now();
        this.status = status;
        this.isHost = isHost;
        this.userNickname = user.getNickName();
        this.userId = user.getId();
        this.profileImagePath = getProfileImagePath();
    }

}