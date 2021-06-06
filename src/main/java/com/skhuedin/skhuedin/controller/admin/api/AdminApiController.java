package com.skhuedin.skhuedin.controller.admin.api;

import com.skhuedin.skhuedin.controller.response.BasicResponse;
import com.skhuedin.skhuedin.controller.response.CommonResponse;
import com.skhuedin.skhuedin.dto.posts.PostsAdminUpdateRequestDto;
import com.skhuedin.skhuedin.infra.MyRole;
import com.skhuedin.skhuedin.infra.Role;
import com.skhuedin.skhuedin.service.CategoryService;
import com.skhuedin.skhuedin.service.PostsService;
import com.skhuedin.skhuedin.service.QuestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Slf4j
public class AdminApiController {

    private final PostsService postsService;
    private final CategoryService categoryService;
    private final QuestionService questionService;

//    @MyRole(role = Role.ADMIN)
    @GetMapping("posts")
    public ResponseEntity<? extends BasicResponse> getPosts(
            Pageable pageable,
            @RequestParam(name = "category", defaultValue = "") String categoryName,
            @RequestParam(name = "username", defaultValue = "") String username) {

        if (categoryName.isEmpty() && !username.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new CommonResponse<>(postsService.findByUserName(pageable, username)));
        } else if (!categoryName.isEmpty() && username.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new CommonResponse<>(postsService.findByCategoryName(pageable, categoryName)));
        } else {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new CommonResponse<>(postsService.findAll(pageable)));
        }
    }

    //    @MyRole(role = Role.ADMIN)
    @GetMapping("posts/{postsId}")
    public ResponseEntity<? extends BasicResponse> getPosts(@PathVariable("postsId") Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponse<>(postsService.findByIdByAdmin(id)));
    }

    //    @MyRole(role = Role.ADMIN)
    @GetMapping("categories")
    public ResponseEntity<? extends BasicResponse> categories() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponse<>(categoryService.findAll()));
    }

    //    @MyRole(role = Role.ADMIN)
    @PutMapping("posts/{postsId}")
    public ResponseEntity<? extends BasicResponse> updatePosts(@RequestBody PostsAdminUpdateRequestDto requestDto) {
        postsService.update(requestDto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

//    @MyRole(role = Role.ADMIN)
    @GetMapping("questions")
    public ResponseEntity<? extends BasicResponse> getQuestions(
            Pageable pageable,
            @RequestParam(name = "writerUser", defaultValue = "") String writerUser,
            @RequestParam(name = "targetUser", defaultValue = "") String targetUser) {

        if (!writerUser.isEmpty() && targetUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new CommonResponse<>(questionService.findByWriterUserName(pageable, writerUser)));
        } else if (writerUser.isEmpty() && !targetUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new CommonResponse<>(questionService.findByTargetUserName(pageable, targetUser)));
        } else {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new CommonResponse<>(questionService.findAll(pageable)));
        }
    }

//    @MyRole(role = Role.ADMIN)
    @GetMapping("questions/{questionId}")
    public ResponseEntity<? extends BasicResponse> getQuestions(@PathVariable("questionId") Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponse<>(questionService.findById(id)));
    }

//    @MyRole(role = Role.ADMIN)
    @GetMapping("suggestions")
    public ResponseEntity<? extends BasicResponse> suggestions(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponse<>(postsService.findBySuggestions(pageable)));
    }
}