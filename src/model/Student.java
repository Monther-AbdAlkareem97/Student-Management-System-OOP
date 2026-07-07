package model;

import java.util.List;
import java.util.ArrayList;

public class Student extends User {

    private String studentId;
    private int classId;        // رقم الصف في الداتابيس
    private String className;   // اسم الصف (للعرض فقط)
    private List<Grade> grades;

    // Constructor
    public Student(int id, String name, String email, String password,
                   String studentId, int classId, String className) {
        super(id, name, email, password);
        this.studentId = studentId;
        this.classId = classId;
        this.className = className;
        this.grades = new ArrayList<>();
    }

    @Override
    public String getInfo() {
        return "Student: " + getName() + " | ID: " + studentId +
               " | الصف: " + className;
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

    public int getClassId() { return classId; }
    public void setClassId(int classId) { this.classId = classId; }

    public String getClassName() { return className; }
    public void setClassName(String className) { this.className = className; }

    public List<Grade> getGrades() { return grades; }
    public void addGrade(Grade grade) { this.grades.add(grade); }

    @Override
    public String toString() {
        return studentId + " - " + getName();
    }
}