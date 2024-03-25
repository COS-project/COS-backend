package com.cos.cercat.mockExam.service;

import com.cos.cercat.certificate.domain.Certificate;
import com.cos.cercat.exception.CustomException;
import com.cos.cercat.exception.ErrorCode;
import com.cos.cercat.mockExam.domain.MockExam;
import com.cos.cercat.mockExam.repository.MockExamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MockExamService {

    private final MockExamRepository mockExamRepository;

    public MockExam getMockExam(Long mockExamId) {
        return mockExamRepository.findById(mockExamId).orElseThrow(() ->
                new CustomException(ErrorCode.MOCK_EXAM_NOT_FOUND));
    }

    public MockExam getMockExam(Certificate certificate, Integer year, Integer round) {
        return mockExamRepository.findMockExamByCertificateAndExamYearAndRound(certificate, year, round);
    }

    public List<MockExam> getMockExamList(Certificate certificate, Integer year) {
        return mockExamRepository.findMockExamByCertificateAndExamYear(certificate, year);
    }

    public List<Integer> getMockExamYears(Certificate certificate) {
        return mockExamRepository.findMockExamYearsByCertificate(certificate.getId());
    }

    public List<Integer> getMockExamRounds(Certificate certificate, Integer examYear) {
        return mockExamRepository.findMockExamRoundsByCertificateAndExamYear(certificate.getId(), examYear);
    }

}