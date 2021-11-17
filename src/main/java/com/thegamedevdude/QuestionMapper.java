package com.thegamedevdude;

public class QuestionMapper {
    public String question;
    public Long questionId;
    public Long userId;
    public String username;

    public QuestionMapper(String question, Long questionId, Long userId, String username) {
        this.question = question;
        this.questionId = questionId;
        this.userId = userId;
        this.username = username;
    }
}
