package com.greatlaboratory.board.domain.posts;

import com.greatlaboratory.board.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Posts extends BaseTimeEntity {
    // Entity 클래스에선 setter메소드는 지양한다.
    // db에 값을 변경하여 삽입할 때엔 생성자를 통해 최종값을 채운 후 db에 삽입한다.
    // Entity 클래스는 데이터베이스와 맞닿는 핵심 클래스이므로 이걸 dto로 사용하면 절대 안된다.

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    @Builder
    public Posts(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
