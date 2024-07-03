package com.example.fastcampusmysql.domain.post.dto;

public record CreatePostCommand (Long memberId, String contents) {

}