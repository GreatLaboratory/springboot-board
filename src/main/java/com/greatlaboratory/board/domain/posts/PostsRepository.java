package com.greatlaboratory.board.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostsRepository extends JpaRepository<Posts, Long> {
    // @Repository 어노테이션을 안 써도 되는 대신,
    // Entity클래스와 EntityRepository인터페이스가 같은 디렉터리에 존재해야 한다.

    // 복잡한 SQL은 그냥 이렇게 쿼리문을 활용하면 된다.
    @Query("SELECT p FROM Posts p ORDER BY p.id DESC")
    List<Posts> findAllDesc();
}
