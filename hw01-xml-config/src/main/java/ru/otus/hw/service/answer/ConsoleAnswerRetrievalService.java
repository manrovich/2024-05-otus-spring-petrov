package ru.otus.hw.service.answer;

import lombok.RequiredArgsConstructor;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.service.IOService;

@RequiredArgsConstructor
public class ConsoleAnswerRetrievalService implements AnswerRetrievalService {

    private final IOService ioService;

    @Override
    public Answer getAnswer(Question question) {
        return readAndValidateUserAnswer(question);
    }

    private Answer readAndValidateUserAnswer(Question question) {
        while (true) {
            try {
                int answerNumber = Integer.parseInt(ioService.read());
                if (answerNumber >= 1 && answerNumber <= question.answers().size()) {
                    return question.answers().get(answerNumber - 1);
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
