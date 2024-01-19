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
    private Long xloc; // 코트 x좌표
    private Long yloc; // 코트 y좌표

    private GameCategory gameCategory; // 게임 종류
    private String startTime;
    private Integer duration;
    private Gender gender; // 성별
    private List<Level> levels = new ArrayList<>();
    private Boolean isBallFlag; // 공 여부
    private Integer attendCount;

    //댓글
    private List<CommentResponse> commentResponseList = new ArrayList<>();
    // 참여자 정보
    private List<GameAttendantResponse> gameAttendantResponseList = new ArrayList<>();

    // 내가 host인지
    private Boolean isHost;

    // 좋아요 수
    private Integer views;

    // 북마크 수
    private Integer bookmarkCount;

    @Builder
    public GameDetailResponse(Long id, String title, String content,String courtName,  Boolean isBallFlag, Integer maxAttend, LocalDateTime createdAt,
                              String address, Long xloc, Long yloc,
                              String startTime, Integer duration,
                              GameCategory gameCategory, Gender gender,  List<Level> levels,
                              List<Attendant> attendants, List<Comment> comments, Boolean isHost, Integer views, Integer bookmarkCount) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.maxAttend = maxAttend;
        this.courtName = courtName;
        this.address = address;
        this.xloc = xloc;
        this.yloc = yloc;
        this.gameCategory = gameCategory;
        this.startTime = startTime;
        this.duration = duration;
        this.gender = gender;
        this.isBallFlag = isBallFlag;
        this.levels = levels;
        this.gameAttendantResponseList = attendants.stream().filter(gameAttendant -> gameAttendant.getStatus().equals(AttendantStatus.APPROVE)).map(GameAttendantResponse::new).toList();
        this.attendCount = gameAttendantResponseList.size();
        this.commentResponseList = levelOrderComments(comments.stream().map(CommentResponse::new).toList());
        this.isHost = isHost;
        this.views = views;
        this.bookmarkCount = bookmarkCount;
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
