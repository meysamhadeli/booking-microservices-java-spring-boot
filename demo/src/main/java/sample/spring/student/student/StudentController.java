package sample.spring.student.student;

import buildingblocks.rabbitmq.RabbitmqPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(path = "api/v1/student")
public class StudentController {
    private final StudentService studentService;
    private final RabbitmqPublisher rabbitmqPublisher;

    public StudentController(StudentService studentService, RabbitmqPublisher rabbitmqPublisher) {
        this.studentService = studentService;
        this.rabbitmqPublisher = rabbitmqPublisher;
    }

    @GetMapping
    public CompletableFuture<ResponseEntity<List<Student>>> getStudents() {
        var t = new StudentCreated(1, "test");
        rabbitmqPublisher.publish(t);
        return studentService.getStudents().thenApply(students -> new ResponseEntity<>(students, HttpStatus.OK));
    }


    @GetMapping("/{id}")
    public CompletableFuture<ResponseEntity<Student>> getStudentById(@PathVariable Long id) {
        return studentService.getStudentById(id).thenApply(student -> new ResponseEntity<>(student, HttpStatus.OK));
    }
}


