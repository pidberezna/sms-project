package com.example.sms.dao;

import com.example.sms.model.Student;
import java.util.ArrayList;

public interface StudentManager {
    void addStudent(Student student);
    boolean removeStudent(String studentID);
    boolean updateStudent(String studentID, Student updatedStudent);
    ArrayList<Student> displayAllStudents();
    double calculateAverageGrade();

    boolean isStudentExists(String id);
}


