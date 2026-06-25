package controller;

import database.DatabaseConnection;
import model.Course;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseController {

    // إضافة مادة جديدة
    public static boolean addCourse(Course course) {
        String sql = "INSERT INTO courses (courseName, teacherName, credits) VALUES (?, ?, ?)";
        
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            
            pstmt.setString(1, course.getCourseName());
            pstmt.setString(2, course.getTeacherName());
            pstmt.setInt(3, course.getCredits());
            
            pstmt.executeUpdate();
            System.out.println("✅ تم إضافة المادة بنجاح");
            return true;
            
        } catch (SQLException e) {
            System.out.println("❌ خطأ في الإضافة: " + e.getMessage());
            return false;
        }
    }

    // عرض كل المواد
    public static List<Course> getAllCourses() {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT * FROM courses";
        
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Course c = new Course(
                    rs.getInt("id"),
                    rs.getString("courseName"),
                    rs.getString("teacherName"),
                    rs.getInt("credits")
                );
                courses.add(c);
            }
            
        } catch (SQLException e) {
            System.out.println("❌ خطأ في العرض: " + e.getMessage());
        }
        
        return courses;
    }

    // تعديل مادة
    public static boolean updateCourse(Course course) {
        String sql = "UPDATE courses SET courseName=?, teacherName=?, credits=? WHERE id=?";
        
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            
            pstmt.setString(1, course.getCourseName());
            pstmt.setString(2, course.getTeacherName());
            pstmt.setInt(3, course.getCredits());
            pstmt.setInt(4, course.getCourseId());
            
            pstmt.executeUpdate();
            System.out.println("✅ تم تعديل المادة بنجاح");
            return true;
            
        } catch (SQLException e) {
            System.out.println("❌ خطأ في التعديل: " + e.getMessage());
            return false;
        }
    }

    // حذف مادة
    public static boolean deleteCourse(int courseId) {
        String sql = "DELETE FROM courses WHERE id=?";
        
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, courseId);
            
            pstmt.executeUpdate();
            System.out.println("✅ تم حذف المادة بنجاح");
            return true;
            
        } catch (SQLException e) {
            System.out.println("❌ خطأ في الحذف: " + e.getMessage());
            return false;
        }
    }
}