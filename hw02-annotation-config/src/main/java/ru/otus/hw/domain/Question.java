package ru.otus.hw.domain;

import ru.otus.hw.exceptions.QuestionReadException;

import java.util.List;

public record Question(String text, List<Answer> answers) {

    public Question(String text, List<Answer> answers) {
        if (!hasCorrectAnswer(answers)) {
            throw new QuestionReadException(
                    String.format("The question does not have a correct answer. Question text: %s", text));
        }

        this.text = text;
        this.answers = List.copyOf(answers);
    }

    private boolean hasCorrectAnswer(List<Answer> answers) {
        for (Answer answer : answers) {
            if (answer.isCorrect()) {
                return true;
            }
        }
        return false;
    }
}
