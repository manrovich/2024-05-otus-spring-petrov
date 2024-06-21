package intergation.ru.otus.hw.service;

import org.apache.commons.lang3.StringUtils;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.hw.config.TestConfig;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.dao.CsvQuestionDao;
import ru.otus.hw.dao.DataLoader;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.dao.ResourceDataLoader;
import ru.otus.hw.service.IOService;
import ru.otus.hw.service.ResultService;
import ru.otus.hw.service.ResultServiceImpl;
import ru.otus.hw.service.StreamsIOService;
import ru.otus.hw.service.StudentService;
import ru.otus.hw.service.StudentServiceImpl;
import ru.otus.hw.service.TestRunnerService;
import ru.otus.hw.service.TestRunnerServiceImpl;
import ru.otus.hw.service.TestService;
import ru.otus.hw.service.TestServiceImpl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
public class TestRunnerServiceImplIntegrationTest {

    @Mock
    private TestFileNameProvider testFileNameProvider;

    @Mock
    private TestConfig testConfig;

    protected TestRunnerService testRunnerService;
    protected OutputStream out;

    private void setUp(String testFileName, String input) {
        int rightAnswerCountToPass = 2;
        given(testFileNameProvider.getTestFileName()).willReturn(testFileName);
        given(testConfig.getRightAnswersCountToPass()).willReturn(rightAnswerCountToPass);

        InputStream in = new ByteArrayInputStream(input.getBytes());
        out = new ByteArrayOutputStream();

        IOService ioService = new StreamsIOService(new PrintStream(out), in);
        DataLoader dataLoader = new ResourceDataLoader();
        QuestionDao questionDao = new CsvQuestionDao(testFileNameProvider, dataLoader);
        TestService testService = new TestServiceImpl(ioService, questionDao);
        StudentService studentService = new StudentServiceImpl(ioService);
        ResultService resultService = new ResultServiceImpl(testConfig, ioService);
        testRunnerService = new TestRunnerServiceImpl(testService, studentService, resultService, ioService);
    }

    @Test
    protected void shouldPassTestAndShowResults() {
        String testFileName = "questions.csv";
        String input = "Roman\nPetrov\n1\ninvalidInput\n3\n";
        setUp(testFileName, input);

        testRunnerService.run();

        SoftAssertions assertions = new SoftAssertions();
        String outContent = out.toString();
        assertions.assertThat(outContent).contains("Student: Roman Petrov");
        assertions.assertThat(outContent).contains("Answered questions count: 2");
        assertions.assertThat(outContent).contains("Right answers count: 2");
        assertions.assertThat(outContent).contains("Congratulations! You passed test!");
        assertions.assertAll();
    }

    @Test
    protected void shouldFailTestAndShowResults() {
        String testFileName = "questions.csv";
        String input = "Roman\nPetrov\n1\n2\n";
        setUp(testFileName, input);

        testRunnerService.run();
        SoftAssertions assertions = new SoftAssertions();
        String outContent = out.toString();
        assertions.assertThat(outContent).contains("Student: Roman Petrov");
        assertions.assertThat(outContent).contains("Answered questions count: 2");
        assertions.assertThat(outContent).contains("Right answers count: 1");
        assertions.assertThat(outContent).contains("Sorry. You fail test.");
        assertions.assertAll();
    }

    @Test
    protected void shouldFailTestDueToIncorrectInputAndShowResults() {
        String testFileName = "questions.csv";
        String input = "Roman\nPetrov\n1\n2\n";
        setUp(testFileName, input);

        testRunnerService.run();
        SoftAssertions assertions = new SoftAssertions();
        String outContent = out.toString();
        assertions.assertThat(outContent).contains("Student: Roman Petrov");
        assertions.assertThat(outContent).contains("Answered questions count: 2");
        assertions.assertThat(outContent).contains("Right answers count: 1");
        assertions.assertThat(outContent).contains("Sorry. You fail test.");
        assertions.assertAll();
    }

    @Test
    protected void shouldFailTestDueToOutOfRangeInputAndShowResults() {
        String testFileName = "questions.csv";
        StringBuilder inputSb = new StringBuilder();
        inputSb.append("Roman\n");
        inputSb.append("Petrov\n");
        inputSb.append("5\n".repeat(StreamsIOService.MAX_ATTEMPTS));
        inputSb.append("3\n");
        setUp(testFileName, inputSb.toString());

        testRunnerService.run();
        SoftAssertions assertions = new SoftAssertions();
        String outContent = out.toString();
        assertions.assertThat(outContent).contains("Student: Roman Petrov");
        assertions.assertThat(outContent).contains("Answered questions count: 2");
        assertions.assertThat(outContent).contains("Right answers count: 1");
        assertions.assertThat(outContent).contains("Sorry. You fail test.");
        assertions.assertAll();
    }

    @Test
    protected void shouldFailTestDueToInvalidInputAndShowResults() {
        String testFileName = "questions.csv";
        StringBuilder inputSb = new StringBuilder();
        inputSb.append("Roman\n");
        inputSb.append("Petrov\n");
        inputSb.append("invalidInput\n".repeat(StreamsIOService.MAX_ATTEMPTS));
        inputSb.append("3\n");
        setUp(testFileName, inputSb.toString());

        testRunnerService.run();
        SoftAssertions assertions = new SoftAssertions();
        String outContent = out.toString();
        assertions.assertThat(outContent).contains("Student: Roman Petrov");
        assertions.assertThat(outContent).contains("Answered questions count: 2");
        assertions.assertThat(outContent).contains("Right answers count: 1");
        assertions.assertThat(outContent).contains("Sorry. You fail test.");
        assertions.assertAll();
    }


    @Test
    protected void shouldPrintErrorMessageWhenQuestionReadExceptionOccurs() {
        String testFileName = "not-exist.csv";
        String input = "Roman\nPetrov\n1\n2\n";
        setUp(testFileName, input);
        lenient().when(testConfig.getRightAnswersCountToPass()).thenReturn(2);

        PrintStream originalErr = System.err;
        OutputStream err = new ByteArrayOutputStream();
        System.setErr(new PrintStream(err));

        try {
            testRunnerService.run();

            SoftAssertions assertions = new SoftAssertions();
            String outContent = out.toString();
            String errContent = err.toString();
            assertions.assertThat(outContent).contains("Failed to load test");
            assertions.assertThat(outContent).doesNotContainIgnoringCase("exception");
            assertions.assertThat(errContent).isEqualTo(StringUtils.EMPTY);
            assertions.assertAll();
        } finally {
            System.setErr(originalErr);
        }
    }
}
