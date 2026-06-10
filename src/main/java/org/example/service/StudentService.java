package org.example.service;

import java.util.List;
import java.util.Optional;
import org.example.model.Student;
import org.example.repository.StudentRepository;

public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        if (studentRepository == null) {
            throw new IllegalArgumentException("Student repository cannot be null.");
        }
        this.studentRepository = studentRepository;
    }

    public Student createStudent(String name, String email, String course) {
        String normalizedName = requireText(name, "Name");
        String normalizedEmail = requireText(email, "Email");
        String normalizedCourse = requireText(course, "Course");
        validateEmail(normalizedEmail);

        return studentRepository.save(new Student(normalizedName, normalizedEmail, normalizedCourse));
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Optional<Student> findStudentById(long id) {
        validateId(id);
        return studentRepository.findById(id);
    }

    public Student updateStudent(long id, String name, String email, String course) {
        validateId(id);

        Student existing = studentRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Student not found for id " + id + "."));
        existing.setName(requireText(name, "Name"));
        existing.setEmail(requireText(email, "Email"));
        existing.setCourse(requireText(course, "Course"));
        validateEmail(existing.getEmail());

        return studentRepository.update(existing);
    }

    public boolean deleteStudent(long id) {
        validateId(id);
        return studentRepository.deleteById(id);
    }

    private String requireText(String value, String fieldName) {
        if (value == null) {
            throw new IllegalArgumentException(fieldName + " cannot be blank.");
        }

        String trimmed = value.trim();
        if (trimmed.isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be blank.");
        }
        return trimmed;
    }

    private void validateEmail(String email) {
        if (!email.contains("@") || email.startsWith("@") || email.endsWith("@")) {
            throw new IllegalArgumentException("Email must be valid.");
        }
    }

    private void validateId(long id) {
        if (id <= 0L) {
            throw new IllegalArgumentException("Student id must be positive.");
        }
    }
}
