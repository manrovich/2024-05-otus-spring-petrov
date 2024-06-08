package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import ru.otus.hw.domain.Question;
import ru.otus.hw.service.formatter.QuestionFormatter;

@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final QuestionFormatter questionFormatter;

    @Override
    public String getFormattedQuestion(Question question) {
        return questionFormatter.format(question);
    }
}
