package ru.otus.hw.service.question;

import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;

public interface QuestionService {

    String getFormattedQuestion(Question question);

    boolean isCorrectAnswer(Question question, Answer answer);
}
