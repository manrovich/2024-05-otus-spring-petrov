package ru.otus.hw.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.exceptions.QuestionReadException;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class CsvQuestionDaoTest {

    @Mock
    private TestFileNameProvider testFileNameProvider;

    @Mock
    private DataLoader dataLoader;

    private CsvQuestionDao csvQuestionDao;

    @BeforeEach
    void setUp() {
        csvQuestionDao = new CsvQuestionDao(testFileNameProvider, dataLoader);
    }

    @DisplayName("загрузка из файла")
    @Test
    void shouldReturnAllQuestionsWithAnswers() throws FileNotFoundException {
        String testFileName = "questions.csv";
        given(testFileNameProvider.getTestFileName()).willReturn(testFileName);
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(testFileName);
        given(dataLoader.load(eq(testFileName))).willReturn(inputStream);

        List<Question> questions = csvQuestionDao.findAll();

        assertThat(questions).containsExactly(
                new Question("Question 1",
                        Arrays.asList(
                                new Answer("Answer 1", true),
                                new Answer("Answer 2", false),
                                new Answer("Answer 3", false)
                        ),
                        new Answer("Answer 1", true)
                ),
                new Question("Question 2",
                        Arrays.asList(
                                new Answer("Answer 4", false),
                                new Answer("Answer 5", false),
                                new Answer("Answer 6", true)
                        ),
                        new Answer("Answer 6", true)
                )
        );
    }

    @DisplayName("ошибка при не существующем файле")
    @Test
    void shouldThrowQuestionReadExceptionWithCauseIllegalArgumentException() throws FileNotFoundException {
        String testFileName = "not-exist.csv";
        given(testFileNameProvider.getTestFileName()).willReturn(testFileName);
        given(dataLoader.load(eq(testFileName)))
                .willThrow(new FileNotFoundException("Resource not found " + testFileName));

        Throwable thrown = catchThrowable(() -> csvQuestionDao.findAll());

        assertThat(thrown).withFailMessage(String.format("File %s not found", testFileName))
                .isInstanceOf(QuestionReadException.class)
                .hasCauseInstanceOf(FileNotFoundException.class);
    }
}