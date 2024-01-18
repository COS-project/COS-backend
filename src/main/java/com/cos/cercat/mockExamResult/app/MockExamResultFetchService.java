package com.cos.cercat.mockExamResult.app;

import com.cos.cercat.certificate.app.CertificateService;
import com.cos.cercat.certificate.domain.Certificate;
import com.cos.cercat.mockExam.app.MockExamService;
import com.cos.cercat.mockExam.domain.MockExam;
import com.cos.cercat.mockExamResult.domain.MockExamResult;
import com.cos.cercat.mockExamResult.dto.response.MockExamResultResponse;
import com.cos.cercat.mockExamResult.dto.response.UserAnswerResponse;
import com.cos.cercat.user.app.UserService;
import com.cos.cercat.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class MockExamResultFetchService {

    private final MockExamResultService mockExamResultService;
    private final MockExamService mockExamService;
    private final UserService userService;
    private final CertificateService certificateService;
    private final UserAnswerService userAnswerService;

    public List<MockExamResultResponse> getMockExamResults(Long mockExamId, Long userId) {
        MockExam mockExam = mockExamService.getMockExam(mockExamId);
        User user = userService.getUser(userId);

        List<MockExamResult> mockExamResults = mockExamResultService.getMockExamResults(mockExam, user);

        return mockExamResults.stream()
                .map(MockExamResultResponse::from)
                .toList();
    }

    /***
     * 자격증 ID와 유저 ID를 통해 유저가 해당 자격증의 전체 모의고사에서 틀린 문제를 조회한다.
     * 한 모의고사를 여러번 응시했을 경우 가장 최근의 틀린 문제가 반영된다.
     * @param pageable
     * @param certificateId
     * @param userId
     * @return
     */
    public Slice<UserAnswerResponse> getAllInCorrectUserAnswers(Pageable pageable, Long certificateId, Long userId) {
        Certificate certificate = certificateService.getCertificate(certificateId);
        User user = userService.getUser(userId);

        return userAnswerService.getAllUserAnswers(pageable, user, certificate).map(UserAnswerResponse::from);
    }

    public Slice<UserAnswerResponse> getInCorrectUserAnswers(Pageable pageable, Long mockExamResultId) {
        MockExamResult mockExamResult = mockExamResultService.getMockExamResult(mockExamResultId);
        return userAnswerService.getUserAnswers(pageable, mockExamResult).map(UserAnswerResponse::from);
    }
}