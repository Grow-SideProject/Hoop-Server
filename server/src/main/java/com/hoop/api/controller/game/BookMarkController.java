package com.hoop.api.controller.game;

import com.hoop.api.config.UserPrincipal;
import com.hoop.api.request.user.FeedbackRequest;
import com.hoop.api.response.BookMarkResponse;
import com.hoop.api.response.DefaultResponse;
import com.hoop.api.service.game.BookMarkService;
import com.hoop.api.service.game.FeedbackService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/bookmark")
public class BookMarkController {

    private final BookMarkService bookMarkService;

    @GetMapping()
    public List<BookMarkResponse> bookMark(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        return bookMarkService.getList(userPrincipal.getUserId());
    }
    @PostMapping()
    public DefaultResponse addBookMark(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody Long gameId) {
        bookMarkService.addBookMark(userPrincipal.getUserId(), gameId);
        return new DefaultResponse();
    }
    @DeleteMapping()
    public DefaultResponse delBookMark(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody Long bookMarkId) {
        bookMarkService.delBookMark(bookMarkId);
        return new DefaultResponse();
    }


}
