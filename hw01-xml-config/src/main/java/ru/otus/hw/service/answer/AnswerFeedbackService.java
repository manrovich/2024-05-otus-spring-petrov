package ru.otus.hw.service.answer;

import ru.otus.hw.domain.Question;

public interface AnswerFeedbackService {

    void handleCorrectAnswer(Question question);

    void handleIncorrectAnswer(Question question);
}
