package com.example.fastcampusmysql.domain.follow.repository;

import com.example.fastcampusmysql.domain.follow.entity.Follow;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class FollowRepository {

    private static final String TABLE_NAME = "follow";

    private static final RowMapper<Follow> ROW_MAPPER = (rs, rowNum) -> Follow.builder()
            .id(rs.getLong("id"))
            .fromMemberId(rs.getLong("fromMemberId"))
            .toMemberId(rs.getLong("toMemberId"))
            .createdAt(rs.getTimestamp("createdAt").toLocalDateTime())
            .build();

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<Follow> findAllByFromMemberId(final Long fromMemberId) {
        final String sql = String.format("SELECT * FROM %s WHERE fromMemberId = :fromMemberId", TABLE_NAME);
        final MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("fromMemberId", fromMemberId);

        return namedParameterJdbcTemplate.query(sql, parameterSource, ROW_MAPPER);
    }

    public Follow save(Follow follow) {
        if (follow.getId() == null) {
            return insert(follow);
        }
        throw new UnsupportedOperationException("지원하지 않는 기능입니다.");
    }

    private Follow insert(Follow follow) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(namedParameterJdbcTemplate.getJdbcTemplate())
                .withTableName(TABLE_NAME)
                .usingGeneratedKeyColumns("id");

        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(follow);
        final long id = simpleJdbcInsert.executeAndReturnKey(parameterSource).longValue();
        return Follow.builder()
                .id(id)
                .fromMemberId(follow.getFromMemberId())
                .toMemberId(follow.getToMemberId())
                .createdAt(follow.getCreatedAt())
                .build();
    }

}
