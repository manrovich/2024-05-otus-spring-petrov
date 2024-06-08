package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import ru.otus.hw.dao.TestDao;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.Test;

import java.util.List;

@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final IOService ioService;

    private final TestDao testDao;

    private final QuestionService questionService;

    @Override
    public void executeTest() {
        ioService.printLine("");
        ioService.printFormattedLine("Please answer the questions below%n");
        Test test = testDao.load();
        List<Question> questions = test.questions();
        for (Question question : questions) {
            printQuestion(question);
            int userAnswer = readAndValidateUserAnswer(question);
            printResult(question, userAnswer);
        }
    }

    private void printQuestion(Question question) {
        ioService.printLine(questionService.getFormattedQuestion(question));
    }

    private void printResult(Question question, int userAnswer) {
        if (isCorrectUserAnswer(question, userAnswer)) {
            ioService.printLine("Correct");
        } else {
            ioService.printLine("Incorrect");
        }
    }

    private boolean isCorrectUserAnswer(Question question, int userAnswer) {
        try {
            return question.answers().get(userAnswer - 1).isCorrect();
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private int readAndValidateUserAnswer(Question question) {
        while (true) {
            try {
                int answer = Integer.parseInt(ioService.readLine());
                if (answer >= 1 && answer <= question.answers().size()) {
                    return answer;
                } else {
                    printIncorrectUserInput(question);
                }
            } catch (NumberFormatException e) {
                printIncorrectUserInput(question);
            }
        }
    }

    private void printIncorrectUserInput(Question question) {
        ioService.printFormattedLine("Incorrect input. Write a number from %s to %s",
                1, question.answers().size());
    }
}
