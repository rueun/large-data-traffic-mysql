package com.example.fastcampusmysql.domain.follow.dto;

import com.example.fastcampusmysql.domain.follow.entity.Follow;

import java.time.LocalDateTime;

public record FollowDto(
        Long id,
        Long fromMemberId,
        Long toMemberId,
        LocalDateTime createdAt) {

    public static FollowDto of(final Follow follow) {
        return new FollowDto(
                follow.getId(),
                follow.getFromMemberId(),
                follow.getToMemberId(),
                follow.getCreatedAt());
    }
}
