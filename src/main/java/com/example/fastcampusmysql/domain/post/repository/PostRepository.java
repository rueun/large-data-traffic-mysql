package com.example.fastcampusmysql.domain.post.repository;

import com.example.fastcampusmysql.domain.post.dto.DailyPostCount;
import com.example.fastcampusmysql.domain.post.dto.DailyPostCountRequest;
import com.example.fastcampusmysql.domain.post.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class PostRepository {

    static final String TABLE_NAME = "post";

    private static final RowMapper<DailyPostCount> DAILY_POST_COUNT_ROW_MAPPER = (rs, rowNum) -> new DailyPostCount(
            rs.getLong("memberId"),
            rs.getDate("createdDate").toLocalDate(),
            rs.getLong("COUNT(id)")
    );
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    public Post save(Post post) {
        if (post.getId() == null) {
            return insert(post);
        }
        return update(post);
    }

    public void bulkInsert(List<Post> posts) {
        String sql = String.format("""
                INSERT INTO %s (memberId, contents, createdDate, createdAt)
                VALUES (:memberId, :contents, :createdDate, :createdAt)
                """, TABLE_NAME);

        SqlParameterSource[] batch = posts.stream()
                .map(BeanPropertySqlParameterSource::new)
                .toArray(SqlParameterSource[]::new);

        namedParameterJdbcTemplate.batchUpdate(sql, batch);
    }

    private Post insert(Post post) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(namedParameterJdbcTemplate.getJdbcTemplate())
                .withTableName(TABLE_NAME)
                .usingGeneratedKeyColumns("id");

        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(post);
        final long id = simpleJdbcInsert.executeAndReturnKey(parameterSource).longValue();

        return Post.builder()
                .id(id)
                .memberId(post.getMemberId())
                .contents(post.getContents())
                .createdDate(post.getCreatedDate())
                .createdAt(post.getCreatedAt())
                .build();
    }

    private Post update(final Post post) {
        throw new UnsupportedOperationException("게시글은 수정할 수 없습니다.");
    }


    public List<DailyPostCount> groupByCreatedDate(final DailyPostCountRequest request) {
        final String sql = String.format("""
                SELECT createdDate, memberId, COUNT(id)
                FROM %s
                WHERE memberId = :memberId AND createdDate BETWEEN :firstDate AND :lastDate
                GROUP BY memberId, createdDate
                """, TABLE_NAME);

        final SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(request);
        return namedParameterJdbcTemplate.query(sql, parameterSource, DAILY_POST_COUNT_ROW_MAPPER);
    }
}
