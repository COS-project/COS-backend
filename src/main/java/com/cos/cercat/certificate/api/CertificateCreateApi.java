package com.cos.cercat.certificate.api;


import com.cos.cercat.certificate.app.CertificateCreateService;
import com.cos.cercat.certificate.dto.request.CertificateCreateRequest;
import com.cos.cercat.certificate.dto.request.ExamInfoCreateRequest;
import com.cos.cercat.global.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "자격증 생성 API")
public class CertificateCreateApi {

    private final CertificateCreateService certificateCreateService;

    @PostMapping("/certificates")
    @Operation(summary = "자격증 생성")
    public Response<Void> createCertificate(@RequestBody CertificateCreateRequest request) {
        certificateCreateService.createCertificate(request);
        return Response.success("자격증 생성 성공");
    }

    @PostMapping("/certificates/{certificateId}/exam-infos")
    @Operation(summary = "자격증 응시 정보 생성")
    public Response<Void> createCertificate(@PathVariable Long certificateId,
                                            @RequestBody ExamInfoCreateRequest request) {
        certificateCreateService.createExamInfo(certificateId, request);
        return Response.success("자격증 응시 정보 생성 성공");
    }
}
