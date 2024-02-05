package com.project.sns.domain.user.application.repository;

import com.project.sns.domain.user.domain.User;
import java.util.Optional;

public interface UserRepository {

    User save(User user);

    Optional<User> findById(Long userId);
}
