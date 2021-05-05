package com.rainyheaven.nature.core.domain.qna;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface QnaRepository extends JpaRepository<Qna, Long> {

    @Query(value = "select q from Qna q join fetch q.user where q.item.id = :itemId",
            countQuery = "select count (q) from Qna q where q.item.id = :itemId")
    Page<Qna> findByItemIdWithUser(@Param("itemId") Long itemId, Pageable pageable);

    @Query(value = "select q from Qna q join fetch q.item where q.user.id = :userId",
            countQuery = "select count (q) from Qna q where q.user.id = :userId")
    Page<Qna> findByUserWithItem(@Param("userId") Long userId, Pageable pageable);

    boolean existsByIdAndUserId(Long id, Long userId);

    int countAllByItemId(Long id);

}
