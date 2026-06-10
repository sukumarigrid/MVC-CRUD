package org.example.repository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import org.example.model.Student;

public class InMemoryStudentRepository implements StudentRepository {
    private final Map<Long, Student> storage = new LinkedHashMap<Long, Student>();
    private final AtomicLong sequence = new AtomicLong(0L);

    @Override
    public synchronized Student save(Student student) {
        Student copy = copyOf(student);

        if (copy.getId() <= 0L) {
            copy.setId(sequence.incrementAndGet());
        } else if (copy.getId() > sequence.get()) {
            sequence.set(copy.getId());
        }

        storage.put(copy.getId(), copy);
        return copyOf(copy);
    }

    @Override
    public synchronized List<Student> findAll() {
        List<Student> students = new ArrayList<Student>(storage.size());
        for (Student student : storage.values()) {
            students.add(copyOf(student));
        }
        return students;
    }

    @Override
    public synchronized Optional<Student> findById(long id) {
        Student student = storage.get(id);
        if (student == null) {
            return Optional.empty();
        }
        return Optional.of(copyOf(student));
    }

    @Override
    public synchronized Student update(Student student) {
        validateStudent(student);

        if (!storage.containsKey(student.getId())) {
            throw new IllegalArgumentException("Student not found for id " + student.getId() + ".");
        }

        Student copy = copyOf(student);
        storage.put(copy.getId(), copy);
        return copyOf(copy);
    }

    @Override
    public synchronized boolean deleteById(long id) {
        return storage.remove(id) != null;
    }

    private void validateStudent(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("Student cannot be null.");
        }
        if (student.getId() <= 0L) {
            throw new IllegalArgumentException("Student id must be positive.");
        }
    }

    private Student copyOf(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("Student cannot be null.");
        }
        return new Student(student.getId(), student.getName(), student.getEmail(), student.getCourse());
    }
}
