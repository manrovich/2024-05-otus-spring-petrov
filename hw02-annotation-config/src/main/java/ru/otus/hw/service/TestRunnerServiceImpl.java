package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.domain.TestResult;
import ru.otus.hw.exceptions.QuestionReadException;

@Service
@RequiredArgsConstructor
public class TestRunnerServiceImpl implements TestRunnerService {

    private final TestService testService;

    private final StudentService studentService;

    private final ResultService resultService;

    private final IOService ioService;

    @Override
    public void run() {
        var student = studentService.determineCurrentStudent();
        TestResult testResult;
        try {
            testResult = testService.executeTestFor(student);
        } catch (QuestionReadException e) {
            ioService.printLine(e.toString());
            ioService.printLine(e.getCause().toString());
            ioService.printLine("Failed to load test");
            return;
        }
        resultService.showResult(testResult);
    }
}