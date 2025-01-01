package sample.spring.student.student;

import lombok.Data;

@Data
public class StudentCreated {
    private int id;
    private String name;

    public StudentCreated() {}

    public StudentCreated(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
