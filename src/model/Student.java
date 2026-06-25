package model;

import java.util.List;
import java.util.ArrayList;

public class Student extends User {
    
    private String studentId;
    private String level;      // المرحلة: اول / ثاني / ثالث
    private String className;  // الصف: A / B / C
    private List<Grade> grades;

    // Constructor
    public Student(int id, String name, String email, String password,
                   String studentId, String level, String className) {
        super(id, name, email, password);
        this.studentId = studentId;
        this.level = level;
        this.className = className;
        this.grades = new ArrayList<>();
    }

    // تطبيق الـ abstract method
    @Override
    public String getInfo() {
        return "Student: " + getName() + " | ID: " + studentId + 
               " | Level: " + level + " | Class: " + className;
    }

    // حساب GPA
    public double calculateGPA() {
        if (grades.isEmpty()) return 0.0;
        double total = 0;
        for (Grade g : grades) {
            total += g.getScore();
        }
        return total / grades.size();
    }

    // Getters & Setters
    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getLevel() { return level; }
    public void setLevel(String level) { this.level = level; }

    public String getClassName() { return className; }
    public void setClassName(String className) { this.className = className; }

    public List<Grade> getGrades() { return grades; }
    public void addGrade(Grade grade) { this.grades.add(grade); }
}