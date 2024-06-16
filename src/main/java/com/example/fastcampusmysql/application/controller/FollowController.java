package com.example.fastcampusmysql.application.controller;

import com.example.fastcampusmysql.application.usecase.CreateFollowMemberUsecase;
import com.example.fastcampusmysql.application.usecase.GetFollowingMembersUsecase;
import com.example.fastcampusmysql.domain.member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/follow")
public class FollowController {
    private final CreateFollowMemberUsecase createFollowMemberUsecase;
    private final GetFollowingMembersUsecase getFollowingMembersUsecase;

    @PostMapping("/{fromMemberId}/{toMemberId}")
    public void follow(@PathVariable final Long fromMemberId, @PathVariable final Long toMemberId) {
        createFollowMemberUsecase.execute(fromMemberId, toMemberId);
    }

    @GetMapping("members/{memberId}")
    public List<MemberDto> getFollowings(@PathVariable final Long memberId) {
        return getFollowingMembersUsecase.execute(memberId);
    }
}
