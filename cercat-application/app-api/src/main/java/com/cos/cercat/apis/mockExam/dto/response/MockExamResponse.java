package com.cos.cercat.apis.mockExam.dto.response;


import com.cos.cercat.apis.certificate.dto.response.CertificateResponse;
import com.cos.cercat.domain.MockExamEntity;

public record MockExamResponse(
        Long MockExamId,
        Integer examYear,
        Integer round,
        Long timeLimit,
        CertificateResponse certificate
) {
    public static MockExamResponse from(MockExamEntity entity) {
        return new MockExamResponse(
                entity.getId(),
                entity.getExamYear(),
                entity.getRound(),
                entity.getTimeLimit(),
                CertificateResponse.from(entity.getCertificateEntity().toDomain())
        );
    }
}
