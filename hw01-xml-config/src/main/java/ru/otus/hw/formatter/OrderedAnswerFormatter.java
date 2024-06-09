package ru.otus.hw.formatter;

import ru.otus.hw.domain.Answer;

import java.util.List;

public class OrderedAnswerFormatter implements AnswerFormatter {

    @Override
    public String format(List<Answer> answers) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < answers.size(); i++) {
            sb.append(i + 1);
            sb.append(". ");
            sb.append(answers.get(i).text());
            sb.append("\n");
        }
        return sb.toString();
    }
}
