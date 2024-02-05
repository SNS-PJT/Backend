package com.project.sns.domain.post.database;

import com.project.sns.domain.post.application.repository.PostRepository;
import com.project.sns.domain.post.domain.Post;
import com.project.sns.domain.user.domain.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class PostRepositoryImpl implements PostRepository {

    private final JPAPostRepository jpaPostRepository;
    private final QueryDslPostRepository queryDslPostRepository;

    @Override

    public Post save(Post post) {
        return jpaPostRepository.save(post);
    }

    @Override
    public List<Post> findAllByUser(User user) {
        return jpaPostRepository.findAllByUser(user);
    }

    @Override
    public List<Post> findAllByKeyword(String keyword) {
        return queryDslPostRepository.findAllByKeyword(keyword);
    }
}
