package com.example.fastcampusmysql.util;

import com.example.fastcampusmysql.domain.post.entity.Post;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.function.Predicate;

import static org.jeasy.random.FieldPredicates.*;

// objectMother, test data builder, fixture factory
public class PostFixtureFactory {

    public static EasyRandom get(Long memberId, LocalDate firstDate, LocalDate lastDate) {

        final Predicate<Field> idPredicate = named("id")
                .and(ofType(Long.class))
                .and(inClass(Post.class));

        final Predicate<Field> memberIdPredicate = named("memberId")
                .and(ofType(Long.class))
                .and(inClass(Post.class));

        EasyRandomParameters parameters = new EasyRandomParameters()
                .excludeField(idPredicate)
                .dateRange(firstDate, lastDate)
                .randomize(memberIdPredicate, () -> memberId);
        return new EasyRandom(parameters);
    }
}
