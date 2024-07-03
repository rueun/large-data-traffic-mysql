package com.example.fastcampusmysql.application.controller;

import com.example.fastcampusmysql.domain.post.dto.CreatePostCommand;
import com.example.fastcampusmysql.domain.post.dto.DailyPostCount;
import com.example.fastcampusmysql.domain.post.dto.DailyPostCountRequest;
import com.example.fastcampusmysql.domain.post.service.PostReadService;
import com.example.fastcampusmysql.domain.post.service.PostWriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/posts")
public class PostController {
    private final PostWriteService postWriteService;
    private final PostReadService postReadService;

    @PostMapping
    public Long create(@RequestBody final CreatePostCommand command) {
        return postWriteService.create(command);
    }

    @GetMapping("/daily-post-counts")
    public List<DailyPostCount> getDailyPostCounts(
            @ModelAttribute DailyPostCountRequest request) {
        return postReadService.getDailyPostCount(request);
    }
}
