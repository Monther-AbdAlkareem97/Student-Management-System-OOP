package model;

public class Grade {
    
    private int gradeId;
    private String courseName;
    private double score;      // الدرجة من 100
    private String status;     // ناجح / راسب

    // Constructor
    public Grade(int gradeId, String courseName, double score) {
        this.gradeId = gradeId;
        this.courseName = courseName;
        this.score = score;
        this.status = score >= 50 ? "ناجح" : "راسب";
    }

    // Getters & Setters
    public int getGradeId() { return gradeId; }
    public void setGradeId(int gradeId) { this.gradeId = gradeId; }

    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }

    public double getScore() { return score; }
    public void setScore(double score) { 
        this.score = score;
        this.status = score >= 50 ? "ناجح" : "راسب";
    }

    public String getStatus() { return status; }
    
    public String getInfo() {
        return "المادة: " + courseName + " | الدرجة: " + score + " | " + status;
    }
}