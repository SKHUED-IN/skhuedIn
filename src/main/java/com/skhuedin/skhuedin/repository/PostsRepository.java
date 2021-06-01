package com.skhuedin.skhuedin.repository;

import com.skhuedin.skhuedin.domain.Posts;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostsRepository extends JpaRepository<Posts, Long> {

    List<Posts> findByBlogIdOrderByLastModifiedDateDesc(Long blogId);

    @Query("select p from Posts p where p.blog.id = :blogId")
    List<Posts> findPostsByBlogId(@Param("blogId") Long blogId);

    @Query("select p from Posts p where p.category.id = :categoryId")
    List<Posts> findPostsByCategoryId(@Param("categoryId") Long categoryId);

    @Query("select p " +
            "from Posts p " +
            "where p.category.id = :categoryId " +
            "order by p.view desc, p.lastModifiedDate")
    List<Posts> findByCategoryIdOrderByView(@Param("categoryId") Long categoryId, Pageable pageable);
}