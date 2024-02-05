package com.project.sns.domain.user.infra.database;

import com.project.sns.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JPAUserRepository extends JpaRepository<User, Long> {

}
