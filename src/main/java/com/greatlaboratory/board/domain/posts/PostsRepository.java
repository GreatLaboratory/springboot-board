package com.greatlaboratory.board.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostsRepository extends JpaRepository<Posts, Long> {
    // @Repository 어노테이션을 안 써도 되는 대신,
    // Entity클래스와 EntityRepository인터페이스가 같은 디렉터리에 존재해야 한다.
}
