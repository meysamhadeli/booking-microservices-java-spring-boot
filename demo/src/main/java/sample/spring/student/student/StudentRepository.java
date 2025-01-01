package sample.spring.student.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.CompletableFuture;


@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    @Async
    CompletableFuture<Student> findStudentById(Long id);
    @Async
    CompletableFuture<List<Student>> findAllBy();
}
