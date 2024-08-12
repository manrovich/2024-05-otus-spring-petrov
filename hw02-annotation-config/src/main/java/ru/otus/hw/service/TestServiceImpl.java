package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private static final int ANSWER_FIRST_NUMBER = 1;

    private final IOService ioService;

    private final QuestionDao questionDao;

    @Override
    public TestResult executeTestFor(Student student) {
        ioService.printLine("");
        ioService.printFormattedLine("Please answer the questions below%n");
        var questions = questionDao.findAll();
        var testResult = new TestResult(student);

        for (var question: questions) {
            boolean isRightAnswer;
            try {
                int answerIndex = printQuestionAndReadAnswerNumber(question) - 1;
                isRightAnswer = isRightAnswer(question, answerIndex);
            } catch (IllegalArgumentException e) {
                isRightAnswer = false;
            }
            testResult.applyAnswer(question, isRightAnswer);
        }
        return testResult;
    }

    private int printQuestionAndReadAnswerNumber(Question question) {
        int answerLastNumber = question.answers().size();
        String errorMessage = String.format("Expected positive number from %d to %d",
                ANSWER_FIRST_NUMBER, answerLastNumber);

        return ioService.readIntForRangeWithPrompt(
                ANSWER_FIRST_NUMBER,
                answerLastNumber,
                formatQuestionWithAnswers(question),
                errorMessage);
    }

    private boolean isRightAnswer(Question question, int answerIndex) {
        return question.answers().get(answerIndex).isCorrect();
    }

    private String formatQuestionWithAnswers(Question question) {
        return question.text() + "\n" + formatAnswers(question.answers());
    }

    private String formatAnswers(List<Answer> answers) {
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
