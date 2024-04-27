package com.cos.cercat.apis.learning.api;

import com.cos.cercat.apis.learning.response.GoalDetailResponse;
import com.cos.cercat.apis.learning.response.GoalResponse;
import com.cos.cercat.certificate.TargetCertificate;
import com.cos.cercat.common.domain.Response;
import com.cos.cercat.learning.Goal;
import com.cos.cercat.learning.GoalAchievement;
import com.cos.cercat.learning.ReadLearningService;
import com.cos.cercat.learning.TargetGoal;
import com.cos.cercat.user.TargetUser;
import com.cos.cercat.user.User;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2")

public class ReadLearningApi implements ReadLearningApiDocs {

    private final ReadLearningService readLearningService;

    @Operation(summary = "유저의 현재까지의 모든 목표 조회")
    @GetMapping("/certificates/{certificateId}/goals")
    public Response<List<GoalResponse>> getAllGoal(@PathVariable Long certificateId,
                                                   @AuthenticationPrincipal User currentUser) {
        List<GoalResponse> responses = readLearningService.getAllGoals(TargetCertificate.from(certificateId), TargetUser.from(currentUser.getId())).stream()
                .map(GoalResponse::from)
                .toList();
        return Response.success(responses);
    }

    @Operation(summary = "목표 상세 조회")
    @GetMapping("/goals/{goalId}")
    public Response<GoalDetailResponse> getGoalDetail(@PathVariable  Long goalId) {
        Goal goal = readLearningService.getGoal(TargetGoal.from(goalId));
        return Response.success(GoalDetailResponse.from(goal));
    }

    @Operation(summary = "목표 달성도 조회")
    @GetMapping("/certificates/{certificateId}/goals/achievement")
    public Response<GoalAchievement> getGoalAchievement(@PathVariable Long certificateId,
                                                        @AuthenticationPrincipal User currentUser) {
        GoalAchievement goalAchievement = readLearningService.getGoalAchievement(TargetCertificate.from(certificateId), TargetUser.from(currentUser.getId()));
        return Response.success(goalAchievement);
    }

    @Operation(summary = "유저 목표 설정 여부 조회")
    @GetMapping("/certificates/{certificateId}/goal-status")
    public Response<Boolean> existsGoal(@PathVariable Long certificateId,
                                        @AuthenticationPrincipal User currentUser) {
        boolean existsGoal = readLearningService.existsGoal(TargetCertificate.from(certificateId), TargetUser.from(currentUser.getId()));
        return Response.success(existsGoal);
    }
}
