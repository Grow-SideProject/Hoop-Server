package com.hoop.api.response;

import com.hoop.api.constant.AttendantStatus;
import com.hoop.api.constant.GameCategory;
import com.hoop.api.constant.Gender;
import com.hoop.api.constant.Level;
import com.hoop.api.domain.Attendant;
import com.hoop.api.domain.Comment;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 서비스 정책에 맞는 클래스
 */
@Getter
@Setter
public class GameDetailResponse {

    private Long id;
    private String title; // 모집 공고명
    private String content; // 모집 내용
    private LocalDateTime createdAt;
    private Integer maxAttend; // 최대 인원
    private String courtName; //코트명
    private String address; // 코트 주소 (추후 분리 예정)
    private GameCategory gameCategory; // 게임 종류
    private String startTime;
    private Integer duration;

    private Gender gender; // 성별

    private List<Level> levels = new ArrayList<>();

    private Boolean isBallFlag; // 공 여부

    private Integer attendCount;

    private List<CommentResponse> commentResponseList = new ArrayList<>();
    private List<AttendantResponse> attendantResponseList = new ArrayList<>();

    private Boolean isHost;
    @Builder
    public GameDetailResponse(Long id, String title, String content, LocalDateTime createdAt,
                              Integer maxAttend, String courtName, String address, GameCategory gameCategory,
                              String startTime, Integer duration, Gender gender, Boolean isBallFlag, List<Level> levels,
                              List<Attendant> attendants, List<Comment> comments, Boolean isHost) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.maxAttend = maxAttend;
        this.courtName = courtName;
        this.address = address;
        this.gameCategory = gameCategory;
        this.startTime = startTime;
        this.duration = duration;
        this.gender = gender;
        this.isBallFlag = isBallFlag;
        this.levels = levels;
        this.attendCount = attendants.stream().filter(gameAttendant -> gameAttendant.getStatus().equals(AttendantStatus.APPROVE)).toList().size();
        this.attendantResponseList = attendants.stream().map(AttendantResponse::new).toList();
        this.commentResponseList = levelOrderComments(comments.stream().map(CommentResponse::new).toList());
        this.isHost = isHost;
    }


    public List<CommentResponse> levelOrderComments(List<CommentResponse> comments) {
        List<CommentResponse> commentResponses = new ArrayList<>();
        for (CommentResponse comment : comments) {
            if (comment.getParentId() == null) {
                commentResponses.add(comment);
            } else {
                for (CommentResponse commentResponse1 : commentResponses) {
                    if (commentResponse1.getId().equals(comment.getParentId())) {
                        commentResponse1.getChildren().add(comment);
                    }
                }
            }
        }
        return commentResponses;
    }

}
