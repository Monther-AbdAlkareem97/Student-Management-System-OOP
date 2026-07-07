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
        String sql = "INSERT INTO courses (courseName, classId, teacherId, credits) VALUES (?, ?, ?, ?)";

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, course.getCourseName());
            pstmt.setInt(2, course.getClassId());
            pstmt.setInt(3, course.getTeacherId());
            pstmt.setInt(4, course.getCredits());

            pstmt.executeUpdate();
            System.out.println("✅ تم إضافة المادة بنجاح");
            return true;

        } catch (SQLException e) {
            System.out.println("❌ خطأ في الإضافة: " + e.getMessage());
            return false;
        }
    }

    // عرض كل المواد (مع اسم الصف واسم الأستاذ)
    public static List<Course> getAllCourses() {
        List<Course> courses = new ArrayList<>();
        String sql = """
            SELECT c.id, c.courseName, c.classId, cl.className,
                   c.teacherId, t.name as teacherName, c.credits
            FROM courses c
            LEFT JOIN classes cl ON c.classId = cl.id
            LEFT JOIN teachers t ON c.teacherId = t.id
        """;

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Course course = new Course(
                    rs.getInt("id"),
                    rs.getString("courseName"),
                    rs.getInt("classId"),
                    rs.getString("className"),
                    rs.getInt("teacherId"),
                    rs.getString("teacherName"),
                    rs.getInt("credits")
                );
                courses.add(course);
            }

        } catch (SQLException e) {
            System.out.println("❌ خطأ في العرض: " + e.getMessage());
        }

        return courses;
    }

    // عرض مواد صف معين
    public static List<Course> getCoursesByClassId(int classId) {
        List<Course> courses = new ArrayList<>();
        String sql = """
            SELECT c.id, c.courseName, c.classId, cl.className,
                   c.teacherId, t.name as teacherName, c.credits
            FROM courses c
            LEFT JOIN classes cl ON c.classId = cl.id
            LEFT JOIN teachers t ON c.teacherId = t.id
            WHERE c.classId = ?
        """;

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, classId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Course course = new Course(
                    rs.getInt("id"),
                    rs.getString("courseName"),
                    rs.getInt("classId"),
                    rs.getString("className"),
                    rs.getInt("teacherId"),
                    rs.getString("teacherName"),
                    rs.getInt("credits")
                );
                courses.add(course);
            }

        } catch (SQLException e) {
            System.out.println("❌ خطأ في العرض: " + e.getMessage());
        }

        return courses;
    }

    // تعديل مادة
    public static boolean updateCourse(Course course) {
        String sql = "UPDATE courses SET courseName=?, classId=?, teacherId=?, credits=? WHERE id=?";

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, course.getCourseName());
            pstmt.setInt(2, course.getClassId());
            pstmt.setInt(3, course.getTeacherId());
            pstmt.setInt(4, course.getCredits());
            pstmt.setInt(5, course.getCourseId());

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