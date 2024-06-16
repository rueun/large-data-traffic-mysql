package com.example.fastcampusmysql.domain.member.repository;

import com.example.fastcampusmysql.domain.member.entity.MemberNicknameHistory;
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
public class MemberNicknameRepository {

    private static final String TABLE_NAME = "memberNicknameHistory";

    private static final RowMapper<MemberNicknameHistory> ROW_MAPPER = (rs, rowNum) -> MemberNicknameHistory.builder()
            .id(rs.getLong("id"))
            .memberId(rs.getLong("memberId"))
            .nickname(rs.getString("nickname"))
            .createdAt(rs.getTimestamp("createdAt").toLocalDateTime())
            .build();

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public Optional<MemberNicknameHistory> findById(final Long id) {

        final String sql = String.format("SELECT * FROM %s WHERE id = :id", TABLE_NAME);
        final MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", id);

        final MemberNicknameHistory history = namedParameterJdbcTemplate.queryForObject(sql, parameterSource, ROW_MAPPER);
        return Optional.ofNullable(history);
    }

    public List<MemberNicknameHistory> findAllByMemberId(final Long memberId) {
        final String sql = String.format("SELECT * FROM %s WHERE memberId = :memberId", TABLE_NAME);
        final MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("memberId", memberId);

        return namedParameterJdbcTemplate.query(sql, parameterSource, ROW_MAPPER);
    }

    public MemberNicknameHistory save(MemberNicknameHistory history) {
        if (history.getId() == null) {
            return insert(history);
        }
        throw new UnsupportedOperationException("변경 이력은 수정할 수 없습니다.");
    }

    private MemberNicknameHistory insert(MemberNicknameHistory history) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(namedParameterJdbcTemplate.getJdbcTemplate())
                .withTableName(TABLE_NAME)
                .usingGeneratedKeyColumns("id");

        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(history);
        final long id = simpleJdbcInsert.executeAndReturnKey(parameterSource).longValue();
        return MemberNicknameHistory.builder()
                .id(id)
                .memberId(history.getMemberId())
                .nickname(history.getNickname())
                .createdAt(history.getCreatedAt())
                .build();
    }

}
