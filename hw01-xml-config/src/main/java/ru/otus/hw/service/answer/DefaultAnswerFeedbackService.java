package ru.otus.hw.service.answer;

import lombok.RequiredArgsConstructor;
import ru.otus.hw.domain.Question;
import ru.otus.hw.service.IOService;

@RequiredArgsConstructor
public class DefaultAnswerFeedbackService implements AnswerFeedbackService {

    private final IOService ioService;

    @Override
    public void handleCorrectAnswer(Question question) {
        ioService.printLine("Correct");
    }

    @Override
    public void handleIncorrectAnswer(Question question) {
        ioService.printLine("Incorrect");
    }
}
