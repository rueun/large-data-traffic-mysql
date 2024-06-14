package com.example.fastcampusmysql.util;

import com.example.fastcampusmysql.domain.member.entity.Member;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;

// objectMother, test data builder, fixture factory
public class MemberFixtureFactory {
    public static Member create() {
        final long seed = System.currentTimeMillis();
        EasyRandomParameters parameters = new EasyRandomParameters().seed(seed);
        return new EasyRandom(parameters).nextObject(Member.class);
    }

    public static Member create(Long seed) {
        EasyRandomParameters parameters = new EasyRandomParameters()
                .seed(seed);
        return new EasyRandom(parameters).nextObject(Member.class);
    }
}
