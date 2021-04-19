package com.roon.board.web.dto;

import com.roon.board.domain.posts.Posts;
import lombok.Getter;

@Getter
public class PostsResponseDto {
    private Long id;
    private String title;
    private String content;
    private String ahthor;

    public PostsResponseDto(Posts entity){
        this.id=entity.getId();
        this.title=entity.getTitle();
        this.content= entity.getContent();
        this.ahthor=entity.getAuthor();
    }
}
