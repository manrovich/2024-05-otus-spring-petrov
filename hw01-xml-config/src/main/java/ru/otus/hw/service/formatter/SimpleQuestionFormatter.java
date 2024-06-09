package ru.otus.hw.service.formatter;

import lombok.RequiredArgsConstructor;
import ru.otus.hw.domain.Question;

@RequiredArgsConstructor
public class SimpleQuestionFormatter implements QuestionFormatter {

    private final AnswerFormatter answerFormatter;

    public String format(Question question) {
        StringBuilder sb = new StringBuilder();
        sb.append(question.text());
        sb.append("\n");
        sb.append(answerFormatter.format(question.answers()));
        return sb.toString();
    }
}
