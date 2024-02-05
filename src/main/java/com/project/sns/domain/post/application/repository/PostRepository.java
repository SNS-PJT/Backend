package com.project.sns.domain.post.application.repository;

import com.project.sns.domain.post.domain.Post;

public interface PostRepository {

    Post save(Post post);
}
