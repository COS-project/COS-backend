package com.cos.cercat.cache;

import com.cos.cercat.mockexam.MockExamCacheRepository;
import com.cos.cercat.mockexam.Question;
import com.cos.cercat.mockexam.TargetMockExam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
@Slf4j
public class MockExamCacheRepositoryImpl implements MockExamCacheRepository {

    private final RedisTemplate<String, List<Question>> redisTemplate;
    private static final Duration USER_CACHE_TTL = Duration.ofDays(3);

    @Override
    public void setQuestions(List<Question> questions) {
        String key = getKey(questions.get(0).mockExam().id());
        log.info("Set Questions from {}", key);
        redisTemplate.opsForValue().setIfAbsent(key, questions, USER_CACHE_TTL);
    }

    @Override
    public Optional<List<Question>> getQuestions(TargetMockExam targetMockExam) {
        String key = getKey(targetMockExam.mockExamId());
        List<Question> questions = redisTemplate.opsForValue().get(key);
        log.info("Get Questions from {}", key);
        return Optional.ofNullable(questions);
    }

    @Override
    public void deleteQuestions(TargetMockExam targetMockExam) {
        String key = getKey(targetMockExam.mockExamId());
        redisTemplate.delete(key);
        log.info("모의고사 문제 캐싱 폐기 완료 - {}", key);
    }

    public String getKey(Long mockExamId) {
        return "MOCK_EXAM:" + mockExamId;
    }

}
