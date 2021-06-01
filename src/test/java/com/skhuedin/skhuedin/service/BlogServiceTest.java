package com.skhuedin.skhuedin.service;

import com.skhuedin.skhuedin.domain.Provider;
import com.skhuedin.skhuedin.domain.User;
import com.skhuedin.skhuedin.dto.blog.BlogMainResponseDto;
import com.skhuedin.skhuedin.dto.blog.BlogSaveRequestDto;
import com.skhuedin.skhuedin.repository.BlogRepository;
import com.skhuedin.skhuedin.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Sql("/truncate.sql")
class BlogServiceTest {

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BlogService blogService;

    User user;

    @BeforeEach
    void beforeEach() {
        user = User.builder()
                .email("user@email.com")
                .password("1234")
                .name("user")
                .userImageUrl("/img")
                .graduationYear("2016")
                .entranceYear("2022")
                .provider(Provider.SELF)
                .build();

        userRepository.save(user);
    }

    @Test
    @DisplayName("dto 를 받아 blog 를 저장하고 조회하는 테스트")
    void save() {

        // given
        BlogSaveRequestDto requestDto = generateBlog();

        // when
        Long saveId = blogService.save(requestDto, 1L);
        BlogMainResponseDto responseDto = blogService.findById(saveId);

        // then
        assertAll(
                () -> assertEquals(saveId, responseDto.getId()),
                () -> assertEquals(requestDto.getContent(), responseDto.getContent())
        );
    }

    @Test
    @DisplayName("존재하지 않는 user 로 인하여 저장하던 중 예외를 던지는 테스트")
    void save_false() {

        // given
        BlogSaveRequestDto requestDto = BlogSaveRequestDto.builder()
                .userId(0L)
                .content("저의 공간에 와주셔서 감사합니다.")
                .build();

        // when & then
        assertThrows(IllegalArgumentException.class, () ->
                blogService.save(requestDto, 1L)
        );
    }

    @Test
    @DisplayName("blog 를 갱신하고 조회하는 테스트")
    void update() {

        // given
        BlogSaveRequestDto requestDto = generateBlog();

        BlogSaveRequestDto updateDto = BlogSaveRequestDto.builder()
                .userId(user.getId())
                .content("user의 책장")
                .build();

        // when
        Long saveId = blogService.save(requestDto, 1L);
        Long updateId = blogService.update(saveId, updateDto, 1L);
        BlogMainResponseDto responseDto = blogService.findById(updateId);

        // then
        assertAll(
                () -> assertEquals(updateId, saveId),
                () -> assertEquals(responseDto.getContent(), updateDto.getContent())
        );
    }

    @Test
    @DisplayName("blog 를 삭제하는 테스트")
    void delete() {

        // given
        BlogSaveRequestDto requestDto = generateBlog();
        Long saveId = blogService.save(requestDto, 1L);

        // when
        blogService.delete(saveId);

        // then
        assertThrows(IllegalArgumentException.class, () ->
                blogService.delete(saveId)
        );
    }

    @Test
    @DisplayName("존재하지 않는 blog 을 삭제하여 예외를 던지는 테스트")
    void delete_false() {

        // given & when & then
        assertThrows(IllegalArgumentException.class, () ->
                blogService.delete(0L)
        );
    }

    private BlogSaveRequestDto generateBlog() {
        return BlogSaveRequestDto.builder()
                .userId(user.getId())
                .content("저의 공간에 와주셔서 감사합니다.")
                .build();
    }

    @AfterEach
    void afterEach() {
        blogRepository.deleteAll();
        userRepository.deleteAll();
    }
}