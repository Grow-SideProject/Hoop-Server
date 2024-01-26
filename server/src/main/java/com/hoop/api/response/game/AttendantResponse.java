package com.hoop.api.response.game;

import com.hoop.api.constant.*;
import com.hoop.api.domain.Attendant;
import com.hoop.api.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 연관관계 클래스
 */
@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class AttendantResponse {
    private Long attendantId;
    private Boolean isHost;
    private AttendantStatus status;
    private LocalDateTime createdAt;
    //game
    private Long gameId;
    private String gameTitle;

    //user
    private Long userId;
    private String userNickname;
    private String profileImagePath;
    private String desc;
    private List<Ability> abilities;
    private PlayStyle playStyle;
    private Level level;

    private Gender gender;

    public AttendantResponse(Attendant attendant) {
        this.attendantId = attendant.getId();
        this.createdAt = attendant.getCreatedAt();
        this.status = attendant.getStatus();
        this.isHost = attendant.getIsHost();
        this.gameId = attendant.getGame().getId();
        this.gameTitle = attendant.getGame().getTitle();
        this.userNickname = attendant.getUser().getNickName();
        this.userId = attendant.getUser().getId();
        this.profileImagePath = attendant.getUser().getProfileImagePath();
        this.desc = attendant.getUser().getDesc();
        this.abilities = attendant.getUser().getAbilities();
        this.playStyle = attendant.getUser().getPlayStyle();
        this.level = attendant.getUser().getLevel();
        this.gender = attendant.getUser().getGender();
    }
    @Builder
    public AttendantResponse(Long id, Boolean isHost, AttendantStatus status, LocalDateTime createdAt, Long gameId, String gameTitle, Long userId, String userNickname, String profileImagePath, String desc, List<Ability> abilities, PlayStyle playStyle, Level level) {
        this.attendantId = id;
        this.isHost = isHost;
        this.status = status;
        this.createdAt = createdAt;
        this.gameId = gameId;
        this.gameTitle = gameTitle;
        this.userId = userId;
        this.userNickname = userNickname;
        this.profileImagePath = profileImagePath;
        this.desc = desc;
        this.abilities = abilities;
        this.playStyle = playStyle;
        this.level = level;
    }
}