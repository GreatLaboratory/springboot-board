package com.greatlaboratory.board.web.dto;

import com.greatlaboratory.board.domain.posts.Posts;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class PostsResponseDto {
    private Long id;
    private String title;
    private String content;
    private String author;

    public PostsResponseDto(Posts postsEntity) {
        this.id = postsEntity.getId();
        this.title = postsEntity.getTitle();
        this.content = postsEntity.getContent();
        this.author = postsEntity.getAuthor();
    }
}
