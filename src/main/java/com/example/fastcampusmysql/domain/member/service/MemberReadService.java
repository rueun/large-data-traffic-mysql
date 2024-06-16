package com.example.fastcampusmysql.domain.member.service;

import com.example.fastcampusmysql.domain.member.dto.MemberDto;
import com.example.fastcampusmysql.domain.member.dto.MemberNicknameHistoryDto;
import com.example.fastcampusmysql.domain.member.entity.Member;
import com.example.fastcampusmysql.domain.member.repository.MemberNicknameRepository;
import com.example.fastcampusmysql.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MemberReadService {

    private final MemberRepository memberRepository;
    private final MemberNicknameRepository memberNicknameHistoryRepository;

    public MemberDto findById(final Long id) {
        final Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));

        return MemberDto.of(member);
    }

    public List<MemberNicknameHistoryDto> getNicknameHistories(final Long memberId) {
        return memberNicknameHistoryRepository.findAllByMemberId(memberId).stream()
                .map(MemberNicknameHistoryDto::of)
                .toList();
    }

}
