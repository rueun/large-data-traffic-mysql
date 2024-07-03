package com.example.fastcampusmysql.application.controller;

import com.example.fastcampusmysql.domain.member.dto.MemberDto;
import com.example.fastcampusmysql.domain.member.dto.MemberNicknameHistoryDto;
import com.example.fastcampusmysql.domain.member.dto.RegisterMemberCommand;
import com.example.fastcampusmysql.domain.member.service.MemberReadService;
import com.example.fastcampusmysql.domain.member.service.MemberWriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberReadService memberReadService;
    private final MemberWriteService memberWriteService;

    @GetMapping("/members/{id}")
    public MemberDto getMember(
            @PathVariable("id") final Long id
    ) {
        return memberReadService.findById(id);
    }

    @PostMapping("/members")
    public MemberDto register(@RequestBody final RegisterMemberCommand command) {
        return memberWriteService.register(command);
    }

    @PutMapping("/members/{id}/nickname")
    public MemberDto changeNickname(
            @PathVariable("id") final Long id,
            @RequestBody final String newNickname
    ) {
        memberWriteService.changeNickname(id, newNickname);
        return memberReadService.findById(id);
    }

    @GetMapping("/members/{id}/nickname-histories")
    public List<MemberNicknameHistoryDto> getNicknameHistories(
            @PathVariable("id") final Long id
    ) {
        return memberReadService.getNicknameHistories(id);
    }
}
