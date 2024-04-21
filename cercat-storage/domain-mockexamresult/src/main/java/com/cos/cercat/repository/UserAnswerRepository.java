package com.cos.cercat.repository;

import com.cos.cercat.domain.UserAnswer;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserAnswerRepository extends JpaRepository<UserAnswer, Long> {

    @Query("""
              SELECT ua FROM UserAnswer ua
              JOIN FETCH ua.questionEntity q
              JOIN ua.subjectResult sr
              JOIN sr.mockExamResult mr
              JOIN mr.mockExamEntity m
              JOIN m.certificateEntity c
              WHERE c.id = :certificateId
                AND (m.id, mr.round) IN (
                  SELECT m2.id, MAX(mr2.round)
                  FROM MockExamResult mr2
                  JOIN mr2.mockExamEntity m2
                  WHERE mr2.userEntity.id = :userId
                  GROUP BY m2.id
                )
                AND ua.isCorrect = false
                AND ua.isReviewed = false
                AND ua.userEntity.id = :userId
              """)
    Slice<UserAnswer> getWrongUserAnswersByUserEntityAndCertificateEntity(Pageable pageable,
                                                              @Param("userId") Long userId,
                                                              @Param("certificateId") Long certificateId);

    @Query("""
            SELECT ua FROM UserAnswer ua
            JOIN FETCH ua.questionEntity q
            JOIN ua.subjectResult sr
            WHERE ua.isCorrect = false
            AND ua.isReviewed = false
            AND sr.mockExamResult.id = :mockExamResultId
            """)
    Slice<UserAnswer> getWrongUserAnswersByMockExamResult(Pageable pageable, @Param("mockExamResultId") Long mockExamResultId);


}