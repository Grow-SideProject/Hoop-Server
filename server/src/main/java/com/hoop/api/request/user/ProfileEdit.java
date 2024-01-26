package com.hoop.api.request.user;

import com.hoop.api.constant.Ability;
import com.hoop.api.constant.Gender;
import com.hoop.api.constant.Level;
import com.hoop.api.constant.PlayStyle;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Data
@NoArgsConstructor
public class ProfileEdit {

    @Pattern(regexp = "^[0-9]{3}-[0-9]{4}-[0-9]{4}$", message = "휴대폰 번호 양식이 올바르지 않습니다.(XXX-XXXX-XXXX)")
    private String phoneNumber;
    @NotBlank(message = "닉네임을 입력해주세요.")
    @Length(min = 3, max = 10, message = "닉네임은 3자 이상 10자 이내로 입력해주세요.")
    private String nickName;
    private Gender gender;
    private String address;
    private String desc;
    private PlayStyle playStyle;
    private String birth;
    private Level level;
    private List<Ability> abilities;

    @Builder
    public ProfileEdit(String phoneNumber, String nickName, Gender gender, String address,
                       String desc, PlayStyle playStyle, String birth, Level level, List<Ability> abilities) {
        this.phoneNumber = phoneNumber;
        this.nickName = nickName;
        this.gender = gender;
        this.address = address;
        this.desc = desc;
        this.playStyle = playStyle;
        this.birth = birth;
        this.level = level;
        this.abilities = abilities;
    }
}