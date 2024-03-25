package com.cos.cercat.mockExam.service;

import com.cos.cercat.exception.CustomException;
import com.cos.cercat.exception.ErrorCode;
import com.cos.cercat.mockExam.domain.MockExam;
import com.cos.cercat.mockExam.domain.Question;
import com.cos.cercat.mockExam.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuestionService {

    private final QuestionRepository questionRepository;

    public List<Question> getQuestionListByMockExam(MockExam mockExam) {
        return questionRepository.findByMockExam(mockExam);
    }

    public Question getQuestion(Long questionId) {
        return questionRepository.findById(questionId).orElseThrow(()
                -> new CustomException(ErrorCode.QUESTION_NOT_FOUND));
    }

    public Question getQuestion(MockExam mockExam, Integer questionSeq) {
        return questionRepository.findQuestionByMockExamAndQuestionSeq(mockExam, questionSeq);
    }
}