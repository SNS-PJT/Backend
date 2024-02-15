package com.project.sns.domain.comment.infra.database;

import com.project.sns.domain.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JPACommentRepository extends JpaRepository<Long, Comment> {

    Comment save(Comment comment);
}
