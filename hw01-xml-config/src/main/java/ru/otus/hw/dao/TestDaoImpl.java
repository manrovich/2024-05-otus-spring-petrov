package ru.otus.hw.dao;

import lombok.RequiredArgsConstructor;
import ru.otus.hw.domain.Test;

@RequiredArgsConstructor
public class TestDaoImpl implements TestDao {

    private final QuestionDao questionDao;

    @Override
    public Test load() {
        return new Test(questionDao.findAll());
    }
}