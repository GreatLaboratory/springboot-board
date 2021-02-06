package com.greatlaboratory.board.service.posts;

import com.greatlaboratory.board.domain.posts.Posts;
import com.greatlaboratory.board.domain.posts.PostsRepository;
import com.greatlaboratory.board.web.dto.PostsListResponseDto;
import com.greatlaboratory.board.web.dto.PostsResponseDto;
import com.greatlaboratory.board.web.dto.PostsSaveRequestDto;
import com.greatlaboratory.board.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService { // service layer에선 비즈니스 로직이 아닌 트랜잭션과 도메인 간의 순서를 보장해주면 된다.
    // 여기에 @Autowired를 안쓴다. @Autowired보다는 생성자로 빈을 주입하는걸 더 권장하는데
    // @RequiredArgsConstructor 덕분에 final로 선언된 모든 필드를 인자값으로 생성하는 생성자가 자동으로 생성된다.
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts postsEntity = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당하는 아이디의 게시글이 없습니다. id="+id));

        // 데이터베이스의 값을 수정하는 작업인데 여기서 postsRepository에 접근하여 쿼리를 날리지 않는다.
        // 트랜잭션 과정 중 데이터베이스로부터 데이터를 select로 가져오면 이 데이터는 영속성 컨텍스트가 유지된다.
        // 이 상태에서 Entity 객체의 값만 변경하면, 트랜잭션이 끝나는 시점에 자동으로 바뀐 부분을 데이터베이스 테이블에 반영시킨다.
        // 즉, Entity 객체의 값만 변경하면 별도로 update쿼리를 데이터베이스에 날릴 필요가 없다는 것이다.
        postsEntity.update(requestDto.getTitle(), requestDto.getContent());
        return id;
    }

    @Transactional
    public void delete(Long id) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다. id="+id));
        postsRepository.delete(posts);
    }

    @Transactional(readOnly = true)
    public PostsResponseDto findById(Long id){
        Posts postsEntity = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당하는 아이디의 게시글이 없습니다. id="+id));
        return new PostsResponseDto(postsEntity);
    }

    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllDesc(){
        return postsRepository.findAllDesc()
                .stream()
                .map(posts -> new PostsListResponseDto(posts))
                .collect(Collectors.toList());
    }
}
