package com.example.fastcampusmysql.application.usecase;

import com.example.fastcampusmysql.domain.follow.service.FollowWriteService;
import com.example.fastcampusmysql.domain.member.service.MemberReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CreateFollowMemberUsecase {
    private final MemberReadService memberReadService;
    private final FollowWriteService followWriteService;

    public void execute(final Long fromMemberId, final Long toMemberId) {
        final var fromMember = memberReadService.findById(fromMemberId);
        final var toMember = memberReadService.findById(toMemberId);

        followWriteService.follow(fromMember, toMember);
    }
}
