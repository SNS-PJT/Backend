package com.project.sns.domain.comment.application.repository;

import com.project.sns.domain.comment.domain.Comment;
import java.util.Optional;

public interface CommentRepository {

    Comment save(Comment comment);

    Optional<Comment> findById(Long id);
}
