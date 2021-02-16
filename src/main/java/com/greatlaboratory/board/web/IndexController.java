package com.greatlaboratory.board.web;

import com.greatlaboratory.board.config.auth.LoginUser;
import com.greatlaboratory.board.config.auth.dto.SessionUser;
import com.greatlaboratory.board.service.posts.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser sessionUser) {
        // index.mustache에 posts 데이터 뿌리기
        model.addAttribute("posts", postsService.findAllDesc());

        // 현재 세션에 user라는 key로 올라가있는 SessionUser객체를 받아서 존재하면 뷰에 userName이라는 이름으로 전달
        // SessionUser sessionUser = (SessionUser) httpSession.getAttribute("user");  -> 위에 메소드 파라미터에 있는 @LoginUser 어노테이션으로 코드 줄이기
        if (sessionUser != null) {
            model.addAttribute("view_user_name", sessionUser.getName());
        }

        // 앞의 경로는 src/main/resources/templates/
        // 뒤의 파일 확장자는 .mustache가 자동으로 지정되어 View Resolver에 의해 처리된다.
        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {
        model.addAttribute("post", postsService.findById(id));
        return "posts-update";
    }
}
