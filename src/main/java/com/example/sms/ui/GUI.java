package com.example.sms.ui;

import com.example.sms.dao.StudentManager;
import com.example.sms.dao.StudentManagerImpl;
import com.example.sms.model.Student;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GUI {
    private final StudentManager studentManager;

    public GUI() {
        studentManager = new StudentManagerImpl();
        initializeGUI();
    }

    private void initializeGUI() {
        // Main application window
        JFrame frame = new JFrame("Student Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        // Input panel for entering student data
        JPanel inputPanel = new JPanel(new GridLayout(5, 2));
        JLabel idLabel = new JLabel("Student ID:");
        JTextField idField = new JTextField();
        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField();
        JLabel ageLabel = new JLabel("Age:");
        JTextField ageField = new JTextField();
        JLabel gradeLabel = new JLabel("Grade:");
        JTextField gradeField = new JTextField();

        // Adding labels and text fields to the input panel
        inputPanel.add(idLabel);
        inputPanel.add(idField);
        inputPanel.add(nameLabel);
        inputPanel.add(nameField);
        inputPanel.add(ageLabel);
        inputPanel.add(ageField);
        inputPanel.add(gradeLabel);
        inputPanel.add(gradeField);

        // Button panel
        JPanel buttonPanel = new JPanel(new GridLayout(1, 5));
        JButton addButton = new JButton("Add Student");
        JButton removeButton = new JButton("Remove Student");
        JButton updateButton = new JButton("Update Student");
        JButton displayButton = new JButton("Display All Students");
        JButton calculateButton = new JButton("Calculate Average");

        // Adding buttons to the panel
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(displayButton);
        buttonPanel.add(calculateButton);

        // Text area for displaying results
        JTextArea outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        // Adding panels to the main window
        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(buttonPanel, BorderLayout.CENTER);
        frame.add(scrollPane, BorderLayout.SOUTH);

        // Handling the "Add Student" button click
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = idField.getText().trim();
                String name = nameField.getText().trim();
                String ageText = ageField.getText().trim();
                String gradeText = gradeField.getText().trim();

                // Validate empty fields
                if (id.isEmpty() || name.isEmpty() || ageText.isEmpty() || gradeText.isEmpty()) {
                    outputArea.append("Error: All fields must be filled!\n");
                    return;
                }

                // Validate ID to contain only digits
                if (!id.matches("\\d+")) {
                    outputArea.append("Error: Student ID must contain only digits!\n");
                    return;
                }

                // Validate the name to contain only letters and spaces
                if (!name.matches("[a-zA-Z ]+")) {
                    outputArea.append("Error: Name must contain only letters and spaces!\n");
                    return;
                }

                // Check if student with the same ID already exists
                if (studentManager.isStudentExists(id)) {
                    outputArea.append("Error: Student with ID " + id + " already exists!\n");
                    return;
                }

                try {
                    int age = Integer.parseInt(ageText);
                    double grade = Double.parseDouble(gradeText);

                    // Validate age and grade ranges
                    if (age <= 0) {
                        outputArea.append("Error: Age must be positive!\n");
                        return;
                    }
                    if (grade < 0.0 || grade > 100.0) {
                        outputArea.append("Error: Grade must be in the range 0.0 - 100.0!\n");
                        return;
                    }

                    // Add a student
                    Student student = new Student(name, age, grade, id);
                    studentManager.addStudent(student);
                    outputArea.append("Student added successfully!\n");
                } catch (NumberFormatException ex) {
                    outputArea.append("Error: Age and grade must be valid numbers!\n");
                } catch (Exception ex) {
                    outputArea.append("Unexpected error: " + ex.getMessage() + "\n");
                }
            }
        });



        // Handling the "Remove Student" button click
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = idField.getText().trim();

                // Validating the Student ID field
                if (id.isEmpty()) {
                    outputArea.append("Error: Student ID cannot be empty!\n");
                    return;
                }

                try {
                    boolean isRemoved = studentManager.removeStudent(id);
                    if (isRemoved) {
                        outputArea.append("Student removed successfully!\n");
                    } else {
                        outputArea.append("Error: Student with ID " + id + " not found.\n");
                    }
                } catch (Exception ex) {
                    outputArea.append("Error removing student: " + ex.getMessage() + "\n");
                }
            }
        });

        // Handling the "Update Student" button click
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = idField.getText().trim();
                String name = nameField.getText().trim();
                String ageText = ageField.getText().trim();
                String gradeText = gradeField.getText().trim();

                // Validate empty fields
                if (id.isEmpty() || name.isEmpty() || ageText.isEmpty() || gradeText.isEmpty()) {
                    outputArea.append("Error: All fields must be filled!\n");
                    return;
                }

                // Validate ID to contain only digits
                if (!id.matches("\\d+")) {
                    outputArea.append("Error: Student ID must contain only digits!\n");
                    return;
                }

                // Validate the name to contain only letters and spaces
                if (!name.matches("[a-zA-Z ]+")) {
                    outputArea.append("Error: Name must contain only letters and spaces!\n");
                    return;
                }

                // Check if student with the same ID exists
                if (!studentManager.isStudentExists(id)) {
                    outputArea.append("Error: Student with ID " + id + " does not exist!\n");
                    return;
                }

                try {
                    int age = Integer.parseInt(ageText);
                    double grade = Double.parseDouble(gradeText);

                    // Validate age and grade ranges
                    if (age <= 0) {
                        outputArea.append("Error: Age must be positive!\n");
                        return;
                    }
                    if (grade < 0.0 || grade > 100.0) {
                        outputArea.append("Error: Grade must be in the range 0.0 - 100.0!\n");
                        return;
                    }

                    // Update a student
                    Student updatedStudent = new Student(name, age, grade, id);
                    boolean isUpdated = studentManager.updateStudent(id, updatedStudent);

                    if (isUpdated) {
                        outputArea.append("Student updated successfully!\n");
                    } else {
                        outputArea.append("Error: Could not update student with ID " + id + ".\n");
                    }
                } catch (NumberFormatException ex) {
                    outputArea.append("Error: Age and grade must be valid numbers!\n");
                } catch (Exception ex) {
                    outputArea.append("Unexpected error: " + ex.getMessage() + "\n");
                }
            }
        });


        // Handling the "Display All Students" button click
        displayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Student> students = studentManager.displayAllStudents();

                if (students.isEmpty()) {
                    outputArea.append("No students found in the database.\n");
                } else {
                    outputArea.append("All Students:\n");
                    for (Student student : students) {
                        outputArea.append(student.getStudentID() + " - " + student.getName() + ", Age: " + student.getAge() + ", Grade: " + student.getGrade() + "\n");
                    }
                }
            }
        });

        // Handling the "Calculate Average" button click
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double average = studentManager.calculateAverageGrade();
                    outputArea.append("Average Grade: " + average + "\n");
                } catch (Exception ex) {
                    outputArea.append("Error calculating average grade: " + ex.getMessage() + "\n");
                }
            }
        });



        // Display the frame
        frame.setVisible(true);
    }
}





