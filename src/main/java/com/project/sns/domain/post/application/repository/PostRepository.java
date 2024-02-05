package com.project.sns.domain.post.application.repository;

import com.project.sns.domain.post.domain.Post;
import com.project.sns.domain.user.domain.User;
import java.util.List;

public interface PostRepository {

    Post save(Post post);

    List<Post> findAllByUser(User user);


}
