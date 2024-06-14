package com.example.fastcampusmysql.domain.member.entity;

import com.example.fastcampusmysql.util.MemberFixtureFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


class MemberTest {

    @DisplayName("회원은 닉네임을 변경할 수 있다.")
    @Test
    void testChangeNickname() {
        // given
        final Member member = MemberFixtureFactory.create();

        final String newNickname = "new";

        // when
        member.changeNickname(newNickname);

        // then
        Assertions.assertEquals(newNickname, member.getNickname());
    }

    @DisplayName("회원의 닉네임은 10자를 초과할 수 없다.")
    @Test
    void testNicknameMaxLength() {
        // given
        final Member member = MemberFixtureFactory.create();

        final String newNickname = "maximumLength";

        // when // then
        Assertions.assertThrows(IllegalArgumentException.class, () -> member.changeNickname(newNickname));
    }
}