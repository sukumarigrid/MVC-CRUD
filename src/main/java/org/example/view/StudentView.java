package org.example.view;

import java.util.List;
import java.util.Scanner;
import org.example.model.Student;

public class StudentView {
    private final Scanner scanner;

    public StudentView(Scanner scanner) {
        if (scanner == null) {
            throw new IllegalArgumentException("Scanner cannot be null.");
        }
        this.scanner = scanner;
    }

    public void showWelcome() {
        System.out.println("========================================");
        System.out.println("      MVC CRUD Student Manager");
        System.out.println("========================================");
    }

    public void showMenu() {
        System.out.println();
        System.out.println("1. Create student");
        System.out.println("2. View all students");
        System.out.println("3. View student by id");
        System.out.println("4. Update student");
        System.out.println("5. Delete student");
        System.out.println("6. Exit");
    }

    public int readMenuChoice() {
        while (true) {
            System.out.print("Select an option: ");
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException ex) {
                System.out.println("Enter a valid number.");
            }
        }
    }

    public String readRequiredText(String prompt) {
        while (true) {
            System.out.print(prompt + ": ");
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println(prompt + " cannot be blank.");
        }
    }

    public long readPositiveLong(String prompt) {
        while (true) {
            System.out.print(prompt + ": ");
            String input = scanner.nextLine().trim();
            try {
                long value = Long.parseLong(input);
                if (value > 0L) {
                    return value;
                }
            } catch (NumberFormatException ex) {
                // fall through to the message below
            }
            System.out.println("Enter a valid positive number.");
        }
    }

    public void showMessage(String message) {
        System.out.println(message);
    }

    public void showStudent(Student student) {
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        System.out.println("ID     : " + student.getId());
        System.out.println("Name   : " + student.getName());
        System.out.println("Email  : " + student.getEmail());
        System.out.println("Course : " + student.getCourse());
    }

    public void showStudents(List<Student> students) {
        if (students == null || students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }

        System.out.printf("%-6s %-20s %-28s %-22s%n", "ID", "Name", "Email", "Course");
        System.out.println("------------------------------------------------------------------------");
        for (Student student : students) {
            System.out.printf(
                "%-6d %-20s %-28s %-22s%n",
                student.getId(),
                student.getName(),
                student.getEmail(),
                student.getCourse()
            );
        }
    }
}
