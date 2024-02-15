package com.project.sns.domain.comment.infra.database;

import com.project.sns.domain.comment.application.repository.CommentRepository;
import com.project.sns.domain.comment.domain.Comment;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepository {

    private final JPACommentRepository jpaCommentRepository;

    @Override
    public Comment save(Comment comment) {
        return jpaCommentRepository.save(comment);
    }

    @Override
    public Optional<Comment> findById(Long id) {
        return jpaCommentRepository.findById(id);
    }
}
