package ru.otus.hw.service.test;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import ru.otus.hw.dao.TestDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.Test;
import ru.otus.hw.service.IOService;
import ru.otus.hw.service.question.QuestionService;
import ru.otus.hw.service.answer.AnswerProcessingService;
import ru.otus.hw.service.answer.AnswerRetrievalService;

import java.util.List;

@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final IOService ioService;

    private final TestDao testDao;

    private final QuestionService questionService;

    private final AnswerProcessingService answerProcessingService;

    private final AnswerRetrievalService answerRetrievalService;

    @Override
    public void executeTest() {
        ioService.printFormattedLine("Please answer the questions below%n");
        Test test = testDao.load();
        List<Question> questions = test.questions();
        for (Question question : questions) {
            printQuestion(question);
            ioService.printLine("Please enter your answer: ");
            Answer userAnswer = answerRetrievalService.getAnswer(question);
            answerProcessingService.processAnswer(question, userAnswer);
            ioService.printLine(StringUtils.EMPTY);
        }
    }

    private void printQuestion(Question question) {
        ioService.printLine(questionService.getFormattedQuestion(question));
    }
}
