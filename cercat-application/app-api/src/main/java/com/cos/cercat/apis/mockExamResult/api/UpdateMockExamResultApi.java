package com.cos.cercat.apis.mockExamResult.api;

import com.cos.cercat.common.domain.Response;
import com.cos.cercat.mockexamresult.TargetUserAnswer;
import com.cos.cercat.mockexamresult.UpdateMockExamResultService;
import com.cos.cercat.user.TargetUser;
import com.cos.cercat.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2")
public class UpdateMockExamResultApi implements UpdateMockExamResultApiDocs{

    private final UpdateMockExamResultService updateMockExamResultService;

    @PatchMapping("/user-answers/{userAnswerId}/review")
    public Response<Void> reviewUserAnswers(@PathVariable Long userAnswerId,
                                            @AuthenticationPrincipal User currentUser) {
        updateMockExamResultService.reviewUserAnswer(TargetUser.from(currentUser.getId()), TargetUserAnswer.from(userAnswerId));
        return Response.success("틀린 문제 리뷰 성공");
    }
}
