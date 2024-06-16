package com.example.fastcampusmysql.domain.member.repository;

import com.example.fastcampusmysql.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class MemberRepository {

    private static final String TABLE_NAME = "member";

    private static final RowMapper<Member> ROW_MAPPER = (rs, rowNum) -> Member.builder()
            .id(rs.getLong("id"))
            .email(rs.getString("email"))
            .nickname(rs.getString("nickname"))
            .birthday(rs.getDate("birthday").toLocalDate())
            .createdAt(rs.getTimestamp("createdAt").toLocalDateTime())
            .build();

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public Optional<Member> findById(final Long id) {

        String sql = String.format("SELECT * FROM %s WHERE id = :id", TABLE_NAME);
        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", id);



        final Member member = namedParameterJdbcTemplate.queryForObject(sql, parameterSource, ROW_MAPPER);
        return Optional.ofNullable(member);
    }

    public Member save(Member member) {
        if (member.getId() == null) {
            return insert(member);
        }
        return update(member);
    }

    private Member insert(Member member) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(namedParameterJdbcTemplate.getJdbcTemplate())
                .withTableName(TABLE_NAME)
                .usingGeneratedKeyColumns("id");

        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(member);
        final long id = simpleJdbcInsert.executeAndReturnKey(parameterSource).longValue();
        return Member.builder()
                .id(id)
                .email(member.getEmail())
                .nickname(member.getNickname())
                .birthday(member.getBirthday())
                .createdAt(member.getCreatedAt())
                .build();
    }

    private Member update(final Member member) {
        final String sql = String.format("UPDATE %s SET email = :email, nickname = :nickname, birthday = :birthday WHERE id = :id", TABLE_NAME);
        final SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(member);
        namedParameterJdbcTemplate.update(sql, parameterSource);
        return member;
    }

    public List<Member> findAllByIdIn(final List<Long> memberIds) {
        if (memberIds.isEmpty()) {
            return List.of();
        }

        final String sql = String.format("SELECT * FROM %s WHERE id IN (:memberIds)", TABLE_NAME);
        final SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("memberIds", memberIds);

        return namedParameterJdbcTemplate.query(sql, parameterSource, ROW_MAPPER);
    }
}
