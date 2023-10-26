package com.hoop.api.response;

import lombok.*;

@ToString
@Data
@NoArgsConstructor
public class KakaoProfile {
    private Long id;
    private Properties properties;
    private KakaoAccount kakao_account;

    @Getter
    private static class Properties {
        private String nickname;
        private String thumbnail_image;
        private String profile_image;
    }

    @Getter
    @NoArgsConstructor
    public class KakaoAccount {
        public Boolean profile_needs_agreement;
        public Boolean has_email;
        public Boolean email_needs_agreement;
        public Boolean is_email_valid;
        public Boolean is_email_verified;
        public String email;
    }
}


