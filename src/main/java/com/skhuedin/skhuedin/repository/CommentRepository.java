package com.skhuedin.skhuedin.repository;

import com.skhuedin.skhuedin.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByQuestionId(Long questionId);
}