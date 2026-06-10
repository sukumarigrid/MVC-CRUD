package org.example;

import java.util.Scanner;
import org.example.controller.StudentController;
import org.example.repository.InMemoryStudentRepository;
import org.example.service.StudentService;
import org.example.view.StudentView;

public class App {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            StudentView view = new StudentView(scanner);
            StudentService service = new StudentService(new InMemoryStudentRepository());
            StudentController controller = new StudentController(service, view);
            controller.run();
        }
    }
}
