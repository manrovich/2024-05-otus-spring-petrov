package ru.otus.hw.service.answer;

import lombok.RequiredArgsConstructor;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.service.question.QuestionService;

@RequiredArgsConstructor
public class DefaultAnswerProcessingService implements AnswerProcessingService {

    private final QuestionService questionService;

    private final AnswerFeedbackService answerFeedbackService;

    @Override
    public void processAnswer(Question question, Answer userAnswer) {
        if (questionService.isCorrectAnswer(question, userAnswer)) {
            answerFeedbackService.handleCorrectAnswer(question);
        } else {
            answerFeedbackService.handleIncorrectAnswer(question);
        }
    }
}
