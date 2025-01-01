package sample.spring.student.student;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table
@Data
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private Integer age;
    private LocalDate birthday;

    public Student() {

    }

    public Student(String name, String email, Integer age, LocalDate birthday) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.birthday = birthday;
    }
}