package model;

public class SchoolClass {

    private int id;
    private String className;  // مثل: "السابع أ"
    private String level;      // مثل: "السابع"

    // Constructor
    public SchoolClass(int id, String className, String level) {
        this.id = id;
        this.className = className;
        this.level = level;
    }

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getClassName() { return className; }
    public void setClassName(String className) { this.className = className; }

    public String getLevel() { return level; }
    public void setLevel(String level) { this.level = level; }

    public String getInfo() {
        return "الصف: " + className + " | المرحلة: " + level;
    }

    @Override
    public String toString() {
        return className + " (" + level + ")";
    }
}