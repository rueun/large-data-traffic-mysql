package com.example.fastcampusmysql.domain.post;

import com.example.fastcampusmysql.domain.post.entity.Post;
import com.example.fastcampusmysql.domain.post.repository.PostRepository;
import com.example.fastcampusmysql.util.PostFixtureFactory;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StopWatch;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
class PostBulkInsertTest {

    @Autowired
    private PostRepository postRepository;


    @Test
    void bulkInsert() {
        final EasyRandom easyRandom = PostFixtureFactory.get(4L,
                LocalDate.of(1970, 6, 1),
                LocalDate.of(2024, 7, 1));

        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        final List<Post> posts = IntStream.range(0, 10000 * 100)
                .mapToObj(i -> easyRandom.nextObject(Post.class))
                .toList();

        stopWatch.stop();
        System.out.println("stopWatch.getTotalTimeSeconds() = " + stopWatch.getTotalTimeSeconds());

        final StopWatch queryStopWatch = new StopWatch();
        queryStopWatch.start();
        postRepository.bulkInsert(posts);
        queryStopWatch.stop();
        System.out.println("queryStopWatch.getTotalTimeSeconds() = " + queryStopWatch.getTotalTimeSeconds());
    }
}