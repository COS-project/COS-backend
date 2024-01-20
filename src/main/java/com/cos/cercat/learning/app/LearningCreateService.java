package com.cos.cercat.learning.app;

import com.cos.cercat.certificate.app.CertificateService;
import com.cos.cercat.certificate.domain.Certificate;
import com.cos.cercat.learning.domain.Goal;
import com.cos.cercat.learning.dto.request.GoalCreateRequest;
import com.cos.cercat.user.app.UserService;
import com.cos.cercat.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class LearningCreateService {

    private final GoalService goalService;
    private final CertificateService certificateService;
    private final UserService userService;
    private final StudyTimeLogService studyTimeLogService;


    /**
     * 목표를 생성한다.
     *
     * @param request 목표 생성 요청 DTO
     * @param certificateId 자격증 ID
     * @param userId 유저 ID
     */
    public void createGoal(GoalCreateRequest request, Long certificateId, Long userId) {
        Certificate certificate = certificateService.getCertificate(certificateId);
        User user = userService.getUser(userId);
        goalService.createGoal(request.toEntity(certificate, user));
    }

    /**
     * 공부시간을 생성한다.
     *
     * @param certificateId 자격증 ID
     * @param studyTime 공부 시간
     * @param userId 유저 ID
     */
    public void createStudyTimeLog(Long certificateId, Long studyTime, Long userId) {
        User user = userService.getUser(userId);
        Certificate certificate = certificateService.getCertificate(certificateId);
        Goal goal = goalService.getGoal(user, certificate);

        studyTimeLogService.createStudyTimeLog(goal, studyTime);
    }
}
