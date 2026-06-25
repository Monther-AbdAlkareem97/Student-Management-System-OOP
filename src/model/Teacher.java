package model;

public class Teacher extends User {
    
    private String teacherId;
    private String subject;        // المادة اللي يدرسها
    private double salary;         // الراتب الأساسي
    private double hoursWorked;    // ساعات العمل

    // Constructor
    public Teacher(int id, String name, String email, String password,
                   String teacherId, String subject, double salary, double hoursWorked) {
        super(id, name, email, password);
        this.teacherId = teacherId;
        this.subject = subject;
        this.salary = salary;
        this.hoursWorked = hoursWorked;
    }

    // تطبيق الـ abstract method
    @Override
    public String getInfo() {
        return "Teacher: " + getName() + " | ID: " + teacherId + 
               " | Subject: " + subject + " | Salary: " + calculateSalary();
    }

    // حساب الراتب
    public double calculateSalary() {
        return salary + (hoursWorked * 10); // راتب أساسي + بدل ساعات
    }

    // Getters & Setters
    public String getTeacherId() { return teacherId; }
    public void setTeacherId(String teacherId) { this.teacherId = teacherId; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public double getSalary() { return salary; }
    public void setSalary(double salary) { this.salary = salary; }

    public double getHoursWorked() { return hoursWorked; }
    public void setHoursWorked(double hoursWorked) { this.hoursWorked = hoursWorked; }
}