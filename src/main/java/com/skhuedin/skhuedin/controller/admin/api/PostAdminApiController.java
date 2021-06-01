package com.skhuedin.skhuedin.controller.admin.api;

import com.skhuedin.skhuedin.dto.posts.PostsAdminResponseDto;
import com.skhuedin.skhuedin.infra.MyRole;
import com.skhuedin.skhuedin.infra.Role;
import com.skhuedin.skhuedin.service.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PostAdminApiController {

    private final PostsService postsService;

    @GetMapping("/postList")
    public String postMainList() {
        return "contents/postList";
    }

    @PostMapping("post/delete")
    public String deletePost(@RequestParam(value = "id", required = false, defaultValue = "0") Long id) {
        postsService.deletePostAdmin(id);
        return "redirect:/postList";
    }

    @MyRole(role = Role.ADMIN)
    @ResponseBody
    @PostMapping("/postList")
    public List<PostsAdminResponseDto> postList() {
        List<PostsAdminResponseDto> list = postsService.findAll();
        return list;
    }


}