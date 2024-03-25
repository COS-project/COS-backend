package com.cos.cercat.learning.app.usecase;

import com.cos.cercat.annotation.UseCase;
import com.cos.cercat.certificate.service.CertificateService;
import com.cos.cercat.certificate.domain.Certificate;
import com.cos.cercat.learning.service.GoalService;
import com.cos.cercat.learning.service.StudyTimeLogService;
import com.cos.cercat.learning.domain.Goal;
import com.cos.cercat.learning.dto.request.GoalCreateRequest;
import com.cos.cercat.user.service.UserService;
import com.cos.cercat.user.domain.User;
import com.cos.cercat.user.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@UseCase
@Slf4j
@RequiredArgsConstructor
public class LearningCreateUseCase {

    private final GoalService goalService;
    private final CertificateService certificateService;
    private final UserService userService;
    private final StudyTimeLogService studyTimeLogService;

    /***
     * 자격증 시험의 목표를 생성합니다.
     * @param request 목표 생성 정보
     * @param certificateId 자격증 ID
     * @param userId 유저 ID
     */
    public void createGoal(GoalCreateRequest request, Long certificateId, Long userId) {
        Certificate certificate = certificateService.getCertificate(certificateId);
        User user = userService.getUser(userId);

        log.info("user - {}, certificate - {} 목표 생성.", user.getEmail(), certificate.getCertificateName());
        goalService.createGoal(request.toEntity(certificate, user));
    }

    /***
     * 유저가 공부시간을 누적한다.
     * @param goalId 목표 ID
     * @param studyTime 공부 시간
     * @param user 유저 정보
     */
    public void createStudyTimeLog(Long goalId, Long studyTime, UserDTO user) {
        Goal goal = goalService.getGoalById(goalId);

        log.info("user - {}, goalId - {}, studyTime - {} 공부 시간 누적", user.getEmail(), goal.getId(), studyTime);
        studyTimeLogService.createStudyTimeLog(goal, studyTime);
    }
}