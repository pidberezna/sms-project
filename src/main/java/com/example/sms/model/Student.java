package com.example.sms.model;

public class Student {
    private String name;
    private int age;
    private double grade;
    private String studentID;

    public Student(String name, int age, double grade, String studentID) {
        this.name = name;
        this.age = age;
        this.grade = grade;
        this.studentID = studentID;
    }

    // Gettery i settery
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if (age <= 0) throw new IllegalArgumentException("Wiek musi być dodatni.");
        this.age = age;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        if (grade < 0.0 || grade > 100.0)
            throw new IllegalArgumentException("Ocena musi być w zakresie 0.0 - 100.0.");
        this.grade = grade;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public void displayInfo() {
        System.out.println("ID: " + studentID + ", Imię: " + name + ", Wiek: " + age + ", Ocena: " + grade);
    }
}


