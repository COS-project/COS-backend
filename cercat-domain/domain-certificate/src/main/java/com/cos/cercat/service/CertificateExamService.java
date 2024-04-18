package com.cos.cercat.service;

import com.cos.cercat.domain.CertificateEntity;
import com.cos.cercat.domain.CertificateExam;
import com.cos.cercat.repository.CertificateExamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CertificateExamService {

    private final CertificateExamRepository certificateExamRepository;

    public void createCertificateExam(CertificateExam certificateExam) {
        certificateExamRepository.save(certificateExam);
    }

    public CertificateExam getRecentCertificateExam(CertificateEntity certificateEntity) {
        return certificateExamRepository.findRecentCertificateExam(certificateEntity.getId());
    }

    public boolean isExamDatePassed(CertificateEntity certificateEntity) {
        CertificateExam recentCertificateExam = getRecentCertificateExam(certificateEntity);

        if (recentCertificateExam == null) {
            return false;
        }

        LocalDateTime todayDateTime = LocalDateTime.now();
        LocalDateTime examDateTime = recentCertificateExam.getExamInfo().getExamSchedule().getExamDateTime();

        return todayDateTime.isAfter(examDateTime);
    }

}
