//package com.hoop.api.service;
//
//import com.hoop.api.domain.User;
//import com.hoop.api.exception.AlreadyExistsEmailException;
//import com.hoop.api.repository.UserRepository;
//import com.hoop.api.request.sign.Signup;
//import com.hoop.api.service.auth.AuthService;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//class AuthServiceTest {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private AuthService authService;
//
//    @AfterEach
//    void clean() {
//        userRepository.deleteAll();
//    }
//
//    @Test
//    @DisplayName("회원가입 성공")
//    void test1() {
//        // given
//        Signup signup = Signup.builder()
//                .email("hodolman88@gmail.com")
//                .password("1234")
//                .name("호돌맨")
//                .build();
//
//        // when
//        authService.signup(signup);
//
//        // then
//        assertEquals(1, userRepository.count());
//
//        User user = userRepository.findAll().iterator().next();
//        assertEquals("hodolman88@gmail.com", user.getEmail());
//        assertNotNull(user.getPassword());
//        assertNotEquals("1234", user.getPassword());
//    }
//
//    @Test
//    @DisplayName("회원가입시 중복된 이메일")
//    void test2() {
//        // given
//        User user = User.builder()
//                .email("hodolman88@gmail.com")
//                .password("1234")
//                .name("짱돌맨")
//                .build();
//        userRepository.save(user);
//
//        Signup signup = Signup.builder()
//                .email("hodolman88@gmail.com")
//                .password("1234")
//                .name("호돌맨")
//                .build();
//
//        // expected
//        assertThrows(AlreadyExistsEmailException.class, () -> authService.signup(signup));
//    }
//}
