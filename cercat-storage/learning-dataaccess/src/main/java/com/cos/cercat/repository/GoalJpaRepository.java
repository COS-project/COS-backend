package com.cos.cercat.repository;

import com.cos.cercat.domain.GoalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface GoalJpaRepository extends JpaRepository<GoalEntity, Long> {

    @Query("""
            SELECT g FROM GoalEntity g
            WHERE g.userEntity.id = :userId
            AND g.certificateEntity.id = :certificateId
            ORDER BY g.prepareStartDateTime DESC
            LIMIT 1
            """)
    Optional<GoalEntity> findRecentGoalByUserIdAndCertificateId(Long userId, Long certificateId);

    @Query("""
            SELECT g FROM GoalEntity g
            WHERE g.userEntity.id = :userId
            AND g.certificateEntity.id = :certificateId
            """)
    boolean existsGoalByUserIdAndCertificateId(Long userId, Long certificateId);

    @Query("""
            SELECT g FROM GoalEntity g
            WHERE g.userEntity.id = :userId
            AND g.certificateEntity.id = :certificateId
            """)
    List<GoalEntity> findGoalsByUserIdAndCertificateId(Long userId, Long certificateId);

}
