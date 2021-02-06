package com.greatlaboratory.board.web;

import com.greatlaboratory.board.service.posts.PostsService;
import com.greatlaboratory.board.web.dto.PostsListResponseDto;
import com.greatlaboratory.board.web.dto.PostsResponseDto;
import com.greatlaboratory.board.web.dto.PostsSaveRequestDto;
import com.greatlaboratory.board.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class PostsApiController {
    // 여기에 @Autowired를 안쓴다. @Autowired보다는 생성자로 빈을 주입하는걸 더 권장하는데
    // @RequiredArgsConstructor 덕분에 final로 선언된 모든 필드를 인자값으로 생성하는 생성자가 자동으로 생성된다.
    private final PostsService postsService;

    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody PostsSaveRequestDto requestDto) {
        return postsService.save(requestDto);
    }

    @PutMapping("/api/v1/posts/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto) {
        return postsService.update(id, requestDto);
    }

    @DeleteMapping("/api/v1/posts/{id}")
    public Long delete(@PathVariable Long id) {
        postsService.delete(id);
        return id;
    }

    @GetMapping("/api/v1/posts/{id}")
    public PostsResponseDto findById(@PathVariable Long id) {
        return postsService.findById(id);
    }

    @GetMapping("/api/v1/posts")
    public List<PostsListResponseDto> findAllDesc() {
        return postsService.findAllDesc();
    }
}
