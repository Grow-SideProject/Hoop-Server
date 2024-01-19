package com.hoop.api.controller;

import com.hoop.api.config.UserPrincipal;
import com.hoop.api.response.BookMarkResponse;
import com.hoop.api.response.DefaultResponse;
import com.hoop.api.service.BookMarkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/book-mark")
public class BookMarkController {

    private final BookMarkService bookMarkService;

    @GetMapping()
    public List<BookMarkResponse> bookMark(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        return bookMarkService.getList(userPrincipal.getUserId());
    }
    @PostMapping("/{gameId}")
    public DefaultResponse addBookMark(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable Long gameId) {
        bookMarkService.addBookMark(userPrincipal.getUserId(), gameId);
        return new DefaultResponse();
    }
    @DeleteMapping("/{gameId}")
    public DefaultResponse delBookMark(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable Long gameId) {
        bookMarkService.delBookMark(userPrincipal.getUserId(), gameId);
        return new DefaultResponse();
    }


}
