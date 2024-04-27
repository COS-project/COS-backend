package com.cos.cercat.learning;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GoalUpdater {

    private final LearningRepository learningRepository;

    public void update(Goal goal) {
        learningRepository.update(goal);
    }
}
