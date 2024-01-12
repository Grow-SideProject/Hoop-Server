package com.hoop.api.request.game;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
public class CommentCreate {
    private Long parentId;

    @Length(min = 10, max = 1000, message = "내용은 10~1000자까지 입력해주세요.")
    @NotBlank(message = "내용을 입력해주세요.")
    private String content;

    @Builder
    public CommentCreate(String content) {
        this.content = content;
    }

}
