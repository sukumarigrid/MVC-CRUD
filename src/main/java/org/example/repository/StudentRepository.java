package org.example.repository;

import java.util.List;
import java.util.Optional;
import org.example.model.Student;

public interface StudentRepository {
    Student save(Student student);

    List<Student> findAll();

    Optional<Student> findById(long id);

    Student update(Student student);

    boolean deleteById(long id);
}
