package model;

public class Course {
    
    private int courseId;
    private String courseName;
    private String teacherName;
    private int credits;       // عدد الساعات

    // Constructor
    public Course(int courseId, String courseName, String teacherName, int credits) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.teacherName = teacherName;
        this.credits = credits;
    }

    // Getters & Setters
    public int getCourseId() { return courseId; }
    public void setCourseId(int courseId) { this.courseId = courseId; }

    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }

    public String getTeacherName() { return teacherName; }
    public void setTeacherName(String teacherName) { this.teacherName = teacherName; }

    public int getCredits() { return credits; }
    public void setCredits(int credits) { this.credits = credits; }

    public String getInfo() {
        return "المادة: " + courseName + " | الأستاذ: " + teacherName + " | الساعات: " + credits;
    }
}