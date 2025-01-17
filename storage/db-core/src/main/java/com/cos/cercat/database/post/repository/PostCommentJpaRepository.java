package com.cos.cercat.database.post.repository;

import com.cos.cercat.database.post.entity.PostCommentEntity;
import com.cos.cercat.database.user.entity.UserEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PostCommentJpaRepository extends JpaRepository<PostCommentEntity, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select pc from PostCommentEntity pc where pc.id = :id")
    Optional<PostCommentEntity> findByIdWithPessimisticLock(Long id);

    Slice<PostCommentEntity> findPostCommentsByUserEntity(UserEntity userEntity, Pageable pageable);

    @Query("select count(pc) from PostCommentEntity pc where pc.postEntity.id = :postId")
    int countByPostId(Long postId);

    @Query("select pc from PostCommentEntity pc where pc.postEntity.id = :postId")
    List<PostCommentEntity> findByPostId(Long postId);

    @Query("""
            select pc from PostCommentEntity pc
            where pc.userEntity.id = :userId
            """)
    Slice<PostCommentEntity>findByUserId(Long userId, PageRequest pageRequest);
}
