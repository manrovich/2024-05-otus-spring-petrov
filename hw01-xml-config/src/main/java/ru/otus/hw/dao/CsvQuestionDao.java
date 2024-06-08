package ru.otus.hw.dao;

import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.config.TestFileSkipLinesProvider;
import ru.otus.hw.dao.dto.QuestionDto;
import ru.otus.hw.domain.Question;
import ru.otus.hw.exceptions.QuestionReadException;

import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

import static ru.otus.hw.utils.FileResourcesUtils.getFileFromResourceAsStream;

@RequiredArgsConstructor
public class CsvQuestionDao implements QuestionDao {
    private final TestFileNameProvider fileNameProvider;
    private final TestFileSkipLinesProvider skipLinesProvider;

    @Override
    public List<Question> findAll() {

        try {
            return findAllQuestionDtos()
                    .stream()
                    .map(QuestionDto::toDomainObject)
                    .collect(Collectors.toList());

        } catch (IllegalArgumentException e) {
            throw new QuestionReadException(String.format("File %s not found", fileNameProvider.getTestFileName()), e);
        } catch (Exception e) {
            throw new QuestionReadException("Unexpected error during load questions", e);
        }
    }

    private List<QuestionDto> findAllQuestionDtos() {
        return new CsvToBeanBuilder<QuestionDto>(getTestReader())
                .withType(QuestionDto.class)
                .withSkipLines(skipLinesProvider.getTestFileSkipLines())
                .build()
                .parse();
    }

    private InputStreamReader getTestReader() {
        return new InputStreamReader(getFileFromResourceAsStream(getClass(), fileNameProvider.getTestFileName()));
    }
}
