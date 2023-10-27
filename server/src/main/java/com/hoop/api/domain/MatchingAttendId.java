package com.hoop.api.domain;


import java.io.Serializable;

/**
 * 식별 관계 복합키 클래스
 */

public class MatchingAttendId implements Serializable {
    private User user;
    private Matching matching; // 엔티티 클래스와 대응하는 필드

    // 생성자, equals 및 hashCode 메서드 구현


    public MatchingAttendId(User user, Matching matching) {
        this.user = user;
        this.matching = matching;
    }
}
