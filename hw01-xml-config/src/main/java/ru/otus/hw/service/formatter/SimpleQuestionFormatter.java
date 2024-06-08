package ru.otus.hw.service.formatter;

import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;

import java.util.List;

public class SimpleQuestionFormatter implements QuestionFormatter {

    public String format(Question question) {
        StringBuilder sb = new StringBuilder();
        sb.append(question.text());
        sb.append("\n");
        sb.append(formatAnswers(question.answers()));
        return sb.toString();
    }

    private StringBuilder formatAnswers(List<Answer> answers) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < answers.size(); i++) {
            sb.append(i + 1);
            sb.append(". ");
            sb.append(answers.get(i).text());
            sb.append("\n");
        }
        return sb;
    }
}
