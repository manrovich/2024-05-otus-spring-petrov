package ru.otus.hw.formatter;

import ru.otus.hw.domain.Answer;

import java.util.List;

public interface AnswerFormatter {

    String format(List<Answer> answers);
}
