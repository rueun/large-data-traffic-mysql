package com.example.fastcampusmysql.application.usecase;

import com.example.fastcampusmysql.domain.follow.dto.FollowDto;
import com.example.fastcampusmysql.domain.follow.service.FollowReadService;
import com.example.fastcampusmysql.domain.post.entity.Post;
import com.example.fastcampusmysql.domain.post.service.PostReadService;
import com.example.fastcampusmysql.util.CursorRequest;
import com.example.fastcampusmysql.util.PageCursor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetTimelinePostUsecase {

    private final FollowReadService followReadService;
    private final PostReadService postReadService;

    // 타임라인은 무한 스크롤 방식이기 때문에 커서 기반 페이징 사용
    public PageCursor<Post> execute(final Long memberId, final CursorRequest cursorRequest) {
        // 1. memberId 로 팔로잉한 사람들을 조회
        final List<FollowDto> followings = followReadService.getFollowings(memberId);
        // 2. 팔로잉한 사람들의 memberId 를 조회
        final List<Long> followingIds = followings.stream()
                .map(FollowDto::toMemberId)
                .toList();
        return postReadService.getPosts(followingIds, cursorRequest);
    }
}
