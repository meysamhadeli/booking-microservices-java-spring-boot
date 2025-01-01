package sample.spring.student.student;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Async
    public CompletableFuture<List<Student>> getStudents() {
        return studentRepository.findAllBy();
    }

    @Async
    public CompletableFuture<Student> getStudentById(Long id) {
        return studentRepository.findStudentById(id);
    }
}
