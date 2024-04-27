package com.cos.cercat.apis.certificate.response;


import com.cos.cercat.certificate.Certificate;

public record CertificateResponse(
        Long certificateId,
        String certificateName
) {
    public static CertificateResponse from(Certificate certificate) {
        return new CertificateResponse(
                certificate.id(),
                certificate.certificateName()
        );
    }
}
