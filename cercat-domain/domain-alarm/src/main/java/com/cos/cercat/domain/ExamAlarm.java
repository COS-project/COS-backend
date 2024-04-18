package com.cos.cercat.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ExamAlarm extends Alarm {

    @ManyToOne
    @JoinColumn(name = "certificate_exam_id")
    private CertificateExam certificateExam;

    @Builder
    public ExamAlarm(UserEntity receiveUserEntity, AlarmType alarmType, Boolean isRead, CertificateExam certificateExam) {
        super(receiveUserEntity, alarmType, isRead);
        this.certificateExam = certificateExam;
    }
}
