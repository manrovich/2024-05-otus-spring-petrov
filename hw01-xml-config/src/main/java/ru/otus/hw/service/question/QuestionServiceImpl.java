package ru.otus.hw.service.question;

import lombok.RequiredArgsConstructor;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.formatter.QuestionFormatter;

@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final QuestionFormatter questionFormatter;

    @Override
    public String getFormattedQuestion(Question question) {
        return questionFormatter.format(question);
    }

    @Override
    public boolean isCorrectAnswer(Question question, Answer answer) {
        return question.correctAnswer().equals(answer);
    }
}
