package com.hoop.api.service.auth;
import com.google.gson.Gson;
import com.hoop.api.domain.User;
import com.hoop.api.repository.UserRepository;
import com.hoop.api.response.KakaoProfile;
import com.hoop.api.exception.CommunicationException;
import com.hoop.api.response.KakaoAuth;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SocialService {

    private final UserRepository userRepository;
    private final RestTemplate restTemplate;
    private final Environment env;
    private final Gson gson;

    /**
     * 카카오 엑세스 토큰을 가져옴
     */
    public KakaoAuth getKakaoTokenInfo(String code) {
        // Set header : Content-type: application/x-www-form-urlencoded
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        // Set parameter => key, value
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", env.getProperty("spring.social.kakao.redirect"));
        params.add("redirect_uri", env.getProperty("spring.social.kakao.client_id"));
        params.add("code", code);
        // Set http entity
        // RestTemplate으로 post 방식으로 key를 요청
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        ResponseEntity<String> response = restTemplate
                .postForEntity(env.getProperty("spring.social.kakao.url.token"), request, String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            return gson.fromJson(response.getBody(), KakaoAuth.class);
        }
        return null;
    }

    /**
     * 카카오 엑세스 토큰으로 카카오 아이디 반환
     */
    public Long getKakaoIdByToken(String accessToken) {
        // Set header : Content-type: application/x-www-form-urlencoded
        HttpHeaders headers = new HttpHeaders();
        //헤더 설정, content type: form url형식으로 , Authorization : Bearer+accessToken으로
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", "Bearer " + accessToken);
        // Set http entity
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(null, headers);
        try {
            // env의 profile을 가져와서 이걸로 post 함
            // restTemplate으로 post 방식으로 key를 요청
            ResponseEntity<String> response = restTemplate.postForEntity(env.getProperty("spring.social.kakao.url.profile"), request, String.class);
            if (response.getStatusCode() == HttpStatus.OK)
                return gson.fromJson(response.getBody(), KakaoProfile.class).getId();
        } catch (Exception e) {
            throw new CommunicationException();
        }
        throw new CommunicationException();
    }


}