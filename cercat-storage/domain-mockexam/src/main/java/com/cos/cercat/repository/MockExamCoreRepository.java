package com.cos.cercat.repository;

import com.cos.cercat.common.exception.CustomException;
import com.cos.cercat.common.exception.ErrorCode;
import com.cos.cercat.domain.MockExamEntity;
import com.cos.cercat.domain.QuestionEntity;
import com.cos.cercat.domain.certificate.TargetCertificate;
import com.cos.cercat.domain.mockexam.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MockExamCoreRepository implements MockExamRepository {

    private final QuestionJpaRepository questionJpaRepository;
    private final MockExamJpaRepository mockExamJpaRepository;

    @Override
    @Transactional(readOnly = true)
    public Question findQuestion(TargetCertificate targetCertificate,
                                 MockExamSession mockExamSession,
                                 int questionSequence) {

        MockExamEntity mockExamEntity = mockExamJpaRepository.findMockExamByCertificateIdAndExamYearAndRound(
                targetCertificate.certificateId(),
                mockExamSession.examYear(),
                mockExamSession.round()
        ).orElseThrow(() -> new CustomException(ErrorCode.MOCK_EXAM_NOT_FOUND));

        return questionJpaRepository.findQuestionByMockExamEntityAndQuestionSeq(mockExamEntity, questionSequence).toDomain();
    }

    @Override
    @Transactional(readOnly = true)
    public List<MockExam> find(TargetCertificate targetCertificate, Integer examYear) {
        return mockExamJpaRepository.findMockExamByCertificateIdAndExamYear(targetCertificate.certificateId(), examYear).stream()
                .map(MockExamEntity::toDomain)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Question> findQuestions(TargetMockExam targetMockExam) {
        return questionJpaRepository.findByMockExamId(targetMockExam.mockExamId()).stream()
                .map(QuestionEntity::toDomain)
                .toList();
    }

    @Override
    public List<Integer> findExamYears(TargetCertificate targetCertificate) {
        return mockExamJpaRepository.findExamYearsByCertificateId(targetCertificate.certificateId());
    }
}
