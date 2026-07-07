package model;

public class Grade {

    private int gradeId;
    private int studentId;      // رقم الطالب في الداتابيس
    private int courseId;       // رقم المادة في الداتابيس
    private String courseName;  // اسم المادة (للعرض فقط)
    private double score;
    private String status;

    // Constructor
    public Grade(int gradeId, int studentId, int courseId, String courseName, double score) {
        this.gradeId = gradeId;
        this.studentId = studentId;
        this.courseId = courseId;
        this.courseName = courseName;
        this.score = score;
        this.status = score >= 50 ? "ناجح" : "راسب";
    }

    public String getInfo() {
        return "المادة: " + courseName + " | الدرجة: " + score + " | " + status;
    }

    // Getters & Setters
    public int getGradeId() { return gradeId; }
    public void setGradeId(int gradeId) { this.gradeId = gradeId; }

    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }

    public int getCourseId() { return courseId; }
    public void setCourseId(int courseId) { this.courseId = courseId; }

    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }

    public double getScore() { return score; }
    public void setScore(double score) {
        this.score = score;
        this.status = score >= 50 ? "ناجح" : "راسب";
    }

    public String getStatus() { return status; }
}