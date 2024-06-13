package com.example.fastcampusmysql.domain.member.service;

import com.example.fastcampusmysql.domain.member.dto.RegisterMemberCommand;
import com.example.fastcampusmysql.domain.member.entity.Member;
import com.example.fastcampusmysql.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberWriteService {

    private final MemberRepository memberRepository;

    public Member register(final RegisterMemberCommand command) {


        final Member member = Member.builder()
                .email(command.email())
                .nickname(command.nickname())
                .birthday(command.birthday())
                .build();

        return memberRepository.save(member);

    }
}
