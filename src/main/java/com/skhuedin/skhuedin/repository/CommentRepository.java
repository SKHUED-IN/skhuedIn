package com.skhuedin.skhuedin.repository;

import com.skhuedin.skhuedin.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("select c from Comment c where c.question.id = :questionId and c.parent is null")
    List<Comment> findByQuestionId(@Param("questionId") Long questionId);
    List<Comment> findByParentId(Long parentId);
}