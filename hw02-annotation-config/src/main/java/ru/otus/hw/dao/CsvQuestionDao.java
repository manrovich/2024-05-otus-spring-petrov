package ru.otus.hw.dao;

import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.dao.dto.QuestionDto;
import ru.otus.hw.domain.Question;
import ru.otus.hw.exceptions.QuestionReadException;

import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class CsvQuestionDao implements QuestionDao {

    private final TestFileNameProvider fileNameProvider;

    private final DataLoader dataLoader;

    @Override
    public List<Question> findAll() {
        try {
            return findAllQuestionDtos()
                    .stream()
                    .map(QuestionDto::toDomainObject)
                    .collect(Collectors.toList());

        } catch (FileNotFoundException e) {
            throw new QuestionReadException(String.format("File %s not found", fileNameProvider.getTestFileName()), e);
        } catch (Exception e) {
            throw new QuestionReadException("Unexpected error during load questions", e);
        }
    }

    private List<QuestionDto> findAllQuestionDtos() throws FileNotFoundException {
        return new CsvToBeanBuilder<QuestionDto>(getTestReader())
                .withType(QuestionDto.class)
                .withSeparator(';')
                .withSkipLines(1)
                .build()
                .parse();
    }

    private InputStreamReader getTestReader() throws FileNotFoundException {
        return new InputStreamReader(dataLoader.load(fileNameProvider.getTestFileName()));
    }
}
