package com.skhuedin.skhuedin.dto.blog;

import com.skhuedin.skhuedin.domain.Blog;
import com.skhuedin.skhuedin.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
public class BlogSaveRequestDto {

    @NotNull(message = "user id는 null이 될 수 없습니다.")
    private Long userId;

    private String profileImageUrl;

    @Size(max = 1000, message = "content의 size는 1000을 넘을 수 없습니다.")
    private String content;

    @Builder
    public BlogSaveRequestDto(Long userId, String profileImageUrl, String content) {
        this.userId = userId;
        this.profileImageUrl = profileImageUrl;
        this.content = content;
    }

    public Blog toEntity(User user) {
        return Blog.builder()
                .user(user)
                .profileImageUrl(this.profileImageUrl)
                .content(this.content)
                .build();
    }
}