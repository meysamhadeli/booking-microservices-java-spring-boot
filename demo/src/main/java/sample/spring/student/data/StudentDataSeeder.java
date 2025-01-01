package sample.spring.student.data;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import sample.spring.student.student.Student;
import sample.spring.student.student.StudentRepository;

import java.time.LocalDate;

@Component
public class StudentDataSeeder implements CommandLineRunner {

    private final StudentRepository studentRepository;

    public StudentDataSeeder(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        seedStudent();
    }

    private void seedStudent() {
        if (studentRepository.count() == 0) {
            Student student = new Student();
            student.setName("john");
            student.setEmail("john@test.com");
            student.setAge(30);
            student.setBirthday(LocalDate.of(1980, 1, 1));
            studentRepository.save(student);
        }
    }
}