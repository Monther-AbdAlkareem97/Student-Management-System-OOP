package model;

public class Course {

    private int courseId;
    private String courseName;
    private int classId;        // رقم الصف في الداتابيس
    private String className;   // اسم الصف (للعرض فقط)
    private int teacherId;      // رقم الأستاذ في الداتابيس
    private String teacherName; // اسم الأستاذ (للعرض فقط)
    private int credits;

    // Constructor
    public Course(int courseId, String courseName, int classId, String className,
                  int teacherId, String teacherName, int credits) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.classId = classId;
        this.className = className;
        this.teacherId = teacherId;
        this.teacherName = teacherName;
        this.credits = credits;
    }

    public String getInfo() {
        return "المادة: " + courseName + " | الصف: " + className +
               " | الأستاذ: " + teacherName + " | الساعات: " + credits;
    }

    @Override
    public String toString() {
        return courseName;
    }

    // Getters & Setters
    public int getCourseId() { return courseId; }
    public void setCourseId(int courseId) { this.courseId = courseId; }

    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }

    public int getClassId() { return classId; }
    public void setClassId(int classId) { this.classId = classId; }

    public String getClassName() { return className; }
    public void setClassName(String className) { this.className = className; }

    public int getTeacherId() { return teacherId; }
    public void setTeacherId(int teacherId) { this.teacherId = teacherId; }

    public String getTeacherName() { return teacherName; }
    public void setTeacherName(String teacherName) { this.teacherName = teacherName; }

    public int getCredits() { return credits; }
    public void setCredits(int credits) { this.credits = credits; }
}