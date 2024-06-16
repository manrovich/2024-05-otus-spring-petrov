package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Question;
import ru.otus.hw.formatter.QuestionFormatter;

import java.util.List;

@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final IOService ioService;

    private final QuestionDao questionDao;

    private final QuestionFormatter questionFormatter;

    @Override
    public void executeTest() {
        ioService.printFormattedLine("Please answer the questions below%n");
        List<Question> questions = questionDao.findAll();
        for (Question question : questions) {
            printQuestion(question);
        }
    }

    private void printQuestion(Question question) {
        ioService.printLine(questionFormatter.format(question));
    }
}
