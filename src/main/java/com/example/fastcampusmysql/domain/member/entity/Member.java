package com.example.fastcampusmysql.domain.member.entity;

import lombok.Builder;
import lombok.Getter;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
public class Member {

    private final static int NICKNAME_MAX_LENGTH = 10;

    final private Long id;
    private String nickname;
    final private String email;
    final private LocalDate birthday;
    final private LocalDateTime createdAt;

    @Builder
    public Member(Long id, String nickname, String email, LocalDate birthday, LocalDateTime createdAt) {
        this.id = id;
        validateNickname(nickname);
        this.nickname = Objects.requireNonNull(nickname);
        this.email = Objects.requireNonNull(email);
        this.birthday = Objects.requireNonNull(birthday);
        this.createdAt = createdAt == null ? LocalDateTime.now() : createdAt;
    }

    public void changeNickname(final String newNickname) {
        Objects.requireNonNull(newNickname);
        validateNickname(newNickname);
        this.nickname = newNickname;
    }

    void validateNickname(final String nickname) {
        Assert.isTrue(nickname.length() <= NICKNAME_MAX_LENGTH, "닉네임은 10자 이하로 입력해주세요.");
    }
}
