package com.project.sns.domain.comment.application.repository;

import com.project.sns.domain.comment.domain.Comment;

public interface CommentRepository {

    Comment save(Comment comment);
}
