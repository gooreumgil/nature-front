package com.rainyheaven.nature.core.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailAndUserStatus(String email, UserStatus userStatus);
    Optional<User> findByIdAndUserStatus(Long id, UserStatus userStatus);

    @Query("select u from User u left join fetch u.addressList where u.id = :id")
    Optional<User> findByIdWithAddress(@Param("id") Long id);

    boolean existsByEmailAndUserStatus(String email, UserStatus userStatus);
}
