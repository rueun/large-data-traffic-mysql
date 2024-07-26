package com.example.fastcampusmysql.util;

import java.util.List;

public record PageCursor<T> (
        CursorRequest nextCursorRequest, // 클라이언트가 다음 페이지를 요청할 때 사용할 커서
        List<T> data){
}
