package org.example.repository;

import java.util.List;
import junit.framework.TestCase;
import org.example.model.Student;

public class InMemoryStudentRepositoryTest extends TestCase {
    public void testSaveFindUpdateAndDelete() {
        InMemoryStudentRepository repository = new InMemoryStudentRepository();

        Student saved = repository.save(new Student("Ravi", "ravi@example.com", "BCA"));
        assertTrue(saved.getId() > 0L);

        Student found = repository.findById(saved.getId()).get();
        assertEquals("Ravi", found.getName());
        assertEquals("ravi@example.com", found.getEmail());

        Student updated = repository.update(
            new Student(saved.getId(), "Ravi Kumar", "ravi.kumar@example.com", "MCA")
        );
        assertEquals("Ravi Kumar", updated.getName());
        assertEquals("MCA", updated.getCourse());

        List<Student> students = repository.findAll();
        assertEquals(1, students.size());

        assertTrue(repository.deleteById(saved.getId()));
        assertFalse(repository.findById(saved.getId()).isPresent());
    }
}
