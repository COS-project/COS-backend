package com.cos.cercat.apis.mockExam.api;

import com.cos.cercat.apis.mockExam.response.MockExamResponse;
import com.cos.cercat.apis.mockExam.response.QuestionResponse;
import com.cos.cercat.common.domain.Response;
import com.cos.cercat.dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Tag(name = "모의고사 조회 API")
public interface MockExamApiDocs {

    @Operation(summary = "년도별 모의고사 가져오기")
    Response<List<MockExamResponse>> findMockExams(Long certificateId,
                                                   Integer examYear,
                                                   UserDTO currentUser);

    @Operation(summary = "모의고사 문제들 가져오기")
    Response<List<QuestionResponse>> findQuestions(Long mockExamId);

    @Operation(summary = "자격증별 시험년도 조회")
    Response<List<Integer>> findExamYears(Long certificateId);

}
