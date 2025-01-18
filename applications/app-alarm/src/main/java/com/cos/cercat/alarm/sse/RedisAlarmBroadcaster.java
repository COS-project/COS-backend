package com.cos.cercat.alarm.sse;

import com.cos.cercat.domain.alarm.Alarm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
public class RedisAlarmBroadcaster {

    static String CHANNEL = "ALARM_CHANNEL";
    private final RedisTemplate<String, Alarm> redisTemplate;

    public void broadcast(Alarm alarm) {
        redisTemplate.convertAndSend(CHANNEL, alarm);

    }
}