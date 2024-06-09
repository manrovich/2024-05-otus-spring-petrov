package ru.otus.hw.service.test;

import lombok.RequiredArgsConstructor;
import ru.otus.hw.exceptions.QuestionReadException;
import ru.otus.hw.service.IOService;

@RequiredArgsConstructor
public class TestRunnerServiceImpl implements TestRunnerService {

    private final TestService testService;

    private final IOService ioService;

    @Override
    public void run() {
        try {
            testService.executeTest();
        } catch (QuestionReadException e) {
            ioService.printLine("Failed to load test");
        }
    }
}
