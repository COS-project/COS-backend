package com.cos.cercat.apis.certificate.api;

import com.cos.cercat.apis.certificate.response.CertificateExamResponse;
import com.cos.cercat.apis.certificate.response.CertificateResponse;
import com.cos.cercat.apis.certificate.response.InterestCertificateResponse;
import com.cos.cercat.domain.certificate.Certificate;
import com.cos.cercat.domain.certificate.CertificateExam;
import com.cos.cercat.domain.certificate.ReadCertificateService;
import com.cos.cercat.domain.certificate.TargetCertificate;
import com.cos.cercat.common.domain.Response;
import com.cos.cercat.domain.user.TargetUser;
import com.cos.cercat.domain.user.User;
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
public class ReadCertificateApi implements ReadCertificateApiDocs{

    private final ReadCertificateService readCertificateService;

    @GetMapping("/certificates")
    public Response<List<CertificateResponse>> getCertificates() {
        List<Certificate> certificates = readCertificateService.readCertificates();
        return Response.success(certificates.stream()
                .map(CertificateResponse::from)
                .toList());
    }

    @GetMapping("/certificates/{certificateId}/certificate-exams")
    public Response<CertificateExamResponse> getCertificateExamDetail(@PathVariable Long certificateId) {
        CertificateExam recentCertificateExam = readCertificateService.readRecentCertificateExam(TargetCertificate.from(certificateId));
        return Response.success(CertificateExamResponse.from(recentCertificateExam));
    }

    @GetMapping("/interest-certificates")
    public Response<List<InterestCertificateResponse>> getInterestCertificates(@AuthenticationPrincipal User currentUser) {
        return Response.success(readCertificateService.readInterestCertificates(TargetUser.from(currentUser.getId())).stream()
                .map(InterestCertificateResponse::from)
                .toList());
    }
}
