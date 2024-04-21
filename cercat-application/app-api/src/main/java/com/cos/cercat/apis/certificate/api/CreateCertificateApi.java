package com.cos.cercat.apis.certificate.api;


import com.cos.cercat.apis.certificate.dto.request.CertificateCreateRequest;
import com.cos.cercat.apis.certificate.dto.request.CertificateExamCreateRequest;
import com.cos.cercat.apis.certificate.dto.request.InterestCertificateCreateRequest;
import com.cos.cercat.common.domain.Response;
import com.cos.cercat.domain.certificate.CreateCertificateService;
import com.cos.cercat.domain.certificate.TargetCertificate;
import com.cos.cercat.domain.user.TargetUser;
import com.cos.cercat.dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "자격증 생성 API")
public class CreateCertificateApi {

    private final CreateCertificateService createCertificateService;

    @PostMapping("/certificates")
    @Operation(summary = "자격증 생성")
    public Response<Void> certificateAdd(@RequestBody CertificateCreateRequest request) {
        createCertificateService.createCertificate(request.certificateName(), request.subjectInfoList());
        return Response.success("자격증 생성 성공");
    }

    @PostMapping("/certificates/{certificateId}/certificate-exams")
    @Operation(summary = "자격증 시험 정보 생성")
    public Response<Void> certificateExamAdd(@PathVariable Long certificateId,
                                            @RequestBody CertificateExamCreateRequest request) {
        createCertificateService.createCertificateExam(TargetCertificate.from(certificateId), request.toExamInformation());
        return Response.success("자격증 시험 정보 생성 성공");
    }

    @PostMapping("/interest-certificates")
    @Operation(summary = "관심 자격증 리스트 생성")
    public Response<Void> InterestCertificateAdd(@RequestBody InterestCertificateCreateRequest request,
                                                     @AuthenticationPrincipal UserDTO currentUser) {
        createCertificateService.addInterestCertificates(TargetUser.from(currentUser.getId()), request.toInterestTargets());
        return Response.success("관심 자격증 리스트 생성 성공");
    }
}