package ru.otus.hw.service.answer;

import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;

public interface AnswerProcessingService {

    void processAnswer(Question question, Answer userAnswer);
}
