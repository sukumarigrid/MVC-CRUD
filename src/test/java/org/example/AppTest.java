package org.example;

import junit.framework.TestCase;
import org.example.model.Student;
import org.example.repository.InMemoryStudentRepository;
import org.example.service.StudentService;

public class AppTest extends TestCase {
    public void testCrudFlow() {
        StudentService service = new StudentService(new InMemoryStudentRepository());

        Student created = service.createStudent("Asha", "asha@example.com", "Computer Science");
        assertTrue(created.getId() > 0L);
        assertEquals("Asha", created.getName());
        assertEquals(1, service.getAllStudents().size());

        Student updated = service.updateStudent(
            created.getId(),
            "Asha Patel",
            "asha.patel@example.com",
            "Software Engineering"
        );
        assertEquals("Asha Patel", updated.getName());
        assertEquals("Software Engineering", updated.getCourse());

        assertTrue(service.deleteStudent(created.getId()));
        assertEquals(0, service.getAllStudents().size());
    }

    public void testCreateRejectsBlankName() {
        StudentService service = new StudentService(new InMemoryStudentRepository());

        try {
            service.createStudent(" ", "test@example.com", "Engineering");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
            assertEquals("Name cannot be blank.", expected.getMessage());
        }
    }
}
