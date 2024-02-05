package com.project.sns.domain.post.database;

import com.project.sns.domain.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JPAPostRepository extends JpaRepository<Post, Long> {

}
