package com.cos.cercat.mockexam;

public record QuestionOption(
        int optionSequence,
        String optionText,
        String optionImageUrl
) {
    public static QuestionOption of(int optionSequence, String optionText, String optionImageUrl) {
        return new QuestionOption(optionSequence, optionText, optionImageUrl);
    }
}
