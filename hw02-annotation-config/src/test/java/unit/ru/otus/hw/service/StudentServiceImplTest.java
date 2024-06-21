package unit.ru.otus.hw.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.hw.domain.Student;
import ru.otus.hw.service.IOService;
import ru.otus.hw.service.StudentService;
import ru.otus.hw.service.StudentServiceImpl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class StudentServiceImplTest {

    @Mock
    IOService ioService;

    @Test
    protected void shouldReturnStudent() {
        given(ioService.readStringWithPrompt("Please input your first name")).willReturn("Roman");
        given(ioService.readStringWithPrompt("Please input your last name")).willReturn("Petrov");

        StudentService studentService = new StudentServiceImpl(ioService);
        Student student = studentService.determineCurrentStudent();
        Student expectedStudent = new Student("Roman", "Petrov");

        assertThat(student).isEqualTo(expectedStudent);
    }
}
