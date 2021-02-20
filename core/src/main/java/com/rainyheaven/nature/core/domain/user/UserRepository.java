package com.rainyheaven.nature.core.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailAndUserStatus(String email, UserStatus userStatus);
    Optional<User> findByIdAndUserStatus(Long id, UserStatus userStatus);
}
