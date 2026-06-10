package org.example.controller;

import java.util.Optional;
import org.example.model.Student;
import org.example.service.StudentService;
import org.example.view.StudentView;

public class StudentController {
    private final StudentService studentService;
    private final StudentView studentView;

    public StudentController(StudentService studentService, StudentView studentView) {
        if (studentService == null) {
            throw new IllegalArgumentException("Student service cannot be null.");
        }
        if (studentView == null) {
            throw new IllegalArgumentException("Student view cannot be null.");
        }
        this.studentService = studentService;
        this.studentView = studentView;
    }

    public void run() {
        studentView.showWelcome();

        boolean running = true;
        while (running) {
            studentView.showMenu();
            int choice = studentView.readMenuChoice();

            switch (choice) {
                case 1:
                    createStudent();
                    break;
                case 2:
                    listStudents();
                    break;
                case 3:
                    viewStudentById();
                    break;
                case 4:
                    updateStudent();
                    break;
                case 5:
                    deleteStudent();
                    break;
                case 6:
                    running = false;
                    studentView.showMessage("Exiting application.");
                    break;
                default:
                    studentView.showMessage("Choose a number between 1 and 6.");
                    break;
            }
        }
    }

    private void createStudent() {
        try {
            String name = studentView.readRequiredText("Enter student name");
            String email = studentView.readRequiredText("Enter student email");
            String course = studentView.readRequiredText("Enter student course");
            Student student = studentService.createStudent(name, email, course);
            studentView.showMessage("Student created successfully.");
            studentView.showStudent(student);
        } catch (IllegalArgumentException ex) {
            studentView.showMessage(ex.getMessage());
        }
    }

    private void listStudents() {
        studentView.showStudents(studentService.getAllStudents());
    }

    private void viewStudentById() {
        long id = studentView.readPositiveLong("Enter student id");
        Optional<Student> student = studentService.findStudentById(id);
        if (student.isPresent()) {
            studentView.showStudent(student.get());
        } else {
            studentView.showMessage("Student not found for id " + id + ".");
        }
    }

    private void updateStudent() {
        try {
            long id = studentView.readPositiveLong("Enter student id to update");
            Optional<Student> currentStudent = studentService.findStudentById(id);
            if (!currentStudent.isPresent()) {
                studentView.showMessage("Student not found for id " + id + ".");
                return;
            }

            studentView.showMessage("Current details:");
            studentView.showStudent(currentStudent.get());

            String name = studentView.readRequiredText("Enter updated name");
            String email = studentView.readRequiredText("Enter updated email");
            String course = studentView.readRequiredText("Enter updated course");
            Student updatedStudent = studentService.updateStudent(id, name, email, course);

            studentView.showMessage("Student updated successfully.");
            studentView.showStudent(updatedStudent);
        } catch (IllegalArgumentException ex) {
            studentView.showMessage(ex.getMessage());
        }
    }

    private void deleteStudent() {
        long id = studentView.readPositiveLong("Enter student id to delete");
        boolean deleted = studentService.deleteStudent(id);
        if (deleted) {
            studentView.showMessage("Student deleted successfully.");
        } else {
            studentView.showMessage("Student not found for id " + id + ".");
        }
    }
}
