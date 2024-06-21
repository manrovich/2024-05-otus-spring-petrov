package unit.ru.otus.hw.domain;

import org.junit.jupiter.api.Test;
import ru.otus.hw.domain.Student;

import static org.assertj.core.api.Assertions.assertThat;

public class StudentTest {

    @Test
    protected void shouldReturnCorrectFullName() {
        Student student = new Student("Roman", "Petrov");
        assertThat(student.getFullName()).isEqualTo("Roman Petrov");
    }
}
