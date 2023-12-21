package com.hoop.api.request.game;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class CommentCreate {

    private Long gameId;
    private String nickName;

    @Length(min = 10, max = 1000, message = "내용은 10~1000자까지 입력해주세요.")
    @NotBlank(message = "내용을 입력해주세요.")
    private String content;

    @Builder
    public CommentCreate(Long gameId, String nickName, String content) {
        this.gameId = gameId;
        this.nickName = nickName;
        this.content = content;
    }

}
