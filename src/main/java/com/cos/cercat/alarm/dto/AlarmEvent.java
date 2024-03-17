package com.cos.cercat.alarm.dto;

import com.cos.cercat.alarm.domain.AlarmType;
import com.cos.cercat.alarm.domain.BoardAlarm;
import com.cos.cercat.user.domain.User;

public record AlarmEvent(
        User receiveUser,
        AlarmArg alarmArg,
        AlarmType alarmType
) {

    public static AlarmEvent of(User receiveUser, AlarmArg alarmArg, AlarmType alarmType) {
        return new AlarmEvent(
                receiveUser,
                alarmArg,
                alarmType
        );
    }

    public BoardAlarm toEntity() {
        return new BoardAlarm(
                receiveUser,
                alarmType,
                false,
                alarmArg.fromUser(),
                alarmArg.postId()
        );
    }
}
