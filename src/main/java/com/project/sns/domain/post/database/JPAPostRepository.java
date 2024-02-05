package com.project.sns.domain.post.database;

import com.project.sns.domain.post.domain.Post;
import com.project.sns.domain.user.domain.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JPAPostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByUser(User user);
}
