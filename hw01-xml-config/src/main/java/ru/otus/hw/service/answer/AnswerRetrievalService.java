package ru.otus.hw.service.answer;

import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;

public interface AnswerRetrievalService {

    Answer getAnswer(Question question);
}
