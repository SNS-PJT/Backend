package com.project.sns.domain.post.database;

import com.project.sns.domain.post.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JPAImageRepository extends JpaRepository<Image, Long> {

}
