package com.example.sms.dao;

import com.example.sms.model.Student;

import java.sql.*;
import java.util.ArrayList;

public class StudentManagerImpl implements StudentManager {
    private static final String DB_URL = "jdbc:sqlite:students.db";

    public StudentManagerImpl() {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String createTableSQL = "CREATE TABLE IF NOT EXISTS students ("
                    + "studentID TEXT PRIMARY KEY, "
                    + "name TEXT NOT NULL, "
                    + "age INTEGER NOT NULL, "
                    + "grade REAL NOT NULL)";

            Statement stmt = conn.createStatement();
            stmt.execute(createTableSQL);
        } catch (SQLException e) {
            System.err.println("Error initializing database: " + e.getMessage());
        }
    }

    @Override
    public boolean isStudentExists(String studentID) {
        String sql = "SELECT COUNT(*) FROM students WHERE studentID = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, studentID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public void addStudent(Student student) {
        String sql = "INSERT INTO students (studentID, name, age, grade) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, student.getStudentID());
            pstmt.setString(2, student.getName());
            pstmt.setInt(3, student.getAge());
            pstmt.setDouble(4, student.getGrade());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            if (e.getErrorCode() == 19) { // SQLITE_CONSTRAINT error code
                System.err.println("Error: Student with ID " + student.getStudentID() + " already exists.");
            } else {
                System.err.println("Error adding student: " + e.getMessage());
            }
        }
    }

    @Override
    public boolean removeStudent(String studentID) {
        String sql = "DELETE FROM students WHERE studentID = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, studentID);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new IllegalArgumentException("Student with ID " + studentID + " does not exist.");
            }
            return true;
        } catch (SQLException e) {
            System.err.println("Error removing student: " + e.getMessage());
            return false;
        } catch (IllegalArgumentException ex) {
            System.err.println(ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean updateStudent(String studentID, Student updatedStudent) {
        String sql = "UPDATE students SET name = ?, age = ?, grade = ? WHERE studentID = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, updatedStudent.getName());
            pstmt.setInt(2, updatedStudent.getAge());
            pstmt.setDouble(3, updatedStudent.getGrade());
            pstmt.setString(4, studentID);
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated == 0) {
                throw new IllegalArgumentException("Student with ID " + studentID + " does not exist.");
            }
            return true;
        } catch (SQLException e) {
            System.err.println("Error updating student: " + e.getMessage());
            return false;
        } catch (IllegalArgumentException ex) {
            System.err.println(ex.getMessage());
            return false;
        }
    }

    @Override
    public ArrayList<Student> displayAllStudents() {
        ArrayList<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                students.add(new Student(
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getDouble("grade"),
                        rs.getString("studentID")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching students: " + e.getMessage());
        }
        return students;
    }

    @Override
    public double calculateAverageGrade() {
        String sql = "SELECT AVG(grade) AS avg_grade FROM students";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getDouble("avg_grade");
            }
        } catch (SQLException e) {
            System.err.println("Error calculating average grade: " + e.getMessage());
        }
        return 0.0;
    }
}




