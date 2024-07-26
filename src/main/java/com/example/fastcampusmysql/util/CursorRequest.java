package com.example.fastcampusmysql.util;

// 커서 키는 중복된 값이 없어야 한다. (유니크 해야 한다.)
public record CursorRequest(Long key, int size) {
    public static final Long NONE_KEY = -1L;

    public boolean hasKey() {
        return key != null;
    }

    // 다음 데이터를 가져오기 위한 커서 키를 반환한다.
    public CursorRequest next(Long key) {
        return new CursorRequest(key, size);
    }
}
