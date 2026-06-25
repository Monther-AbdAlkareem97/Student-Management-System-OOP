package controller;

import database.DatabaseConnection;
import model.Grade;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GradeController {

    // إضافة درجة لطالب معين (studentId هنا هو id الطالب في جدول students)
    public static boolean addGrade(int studentId, Grade grade) {
        String sql = "INSERT INTO grades (studentId, courseName, score, status) VALUES (?, ?, ?, ?)";
        
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            
            pstmt.setInt(1, studentId);
            pstmt.setString(2, grade.getCourseName());
            pstmt.setDouble(3, grade.getScore());
            pstmt.setString(4, grade.getStatus());
            
            pstmt.executeUpdate();
            System.out.println("✅ تم إضافة الدرجة بنجاح");
            return true;
            
        } catch (SQLException e) {
            System.out.println("❌ خطأ في الإضافة: " + e.getMessage());
            return false;
        }
    }

    // عرض كل درجات طالب معين
    public static List<Grade> getGradesByStudentId(int studentId) {
        List<Grade> grades = new ArrayList<>();
        String sql = "SELECT * FROM grades WHERE studentId=?";
        
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, studentId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Grade g = new Grade(
                    rs.getInt("id"),
                    rs.getString("courseName"),
                    rs.getDouble("score")
                );
                grades.add(g);
            }
            
        } catch (SQLException e) {
            System.out.println("❌ خطأ في العرض: " + e.getMessage());
        }
        
        return grades;
    }

    // عرض كل الدرجات (لكل الطلاب)
    public static List<Grade> getAllGrades() {
        List<Grade> grades = new ArrayList<>();
        String sql = "SELECT * FROM grades";
        
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Grade g = new Grade(
                    rs.getInt("id"),
                    rs.getString("courseName"),
                    rs.getDouble("score")
                );
                grades.add(g);
            }
            
        } catch (SQLException e) {
            System.out.println("❌ خطأ في العرض: " + e.getMessage());
        }
        
        return grades;
    }

    // حساب GPA لطالب معين
    public static double calculateGPA(int studentId) {
        List<Grade> grades = getGradesByStudentId(studentId);
        if (grades.isEmpty()) return 0.0;
        
        double total = 0;
        for (Grade g : grades) {
            total += g.getScore();
        }
        return total / grades.size();
    }

    // تعديل درجة
    public static boolean updateGrade(Grade grade) {
        String sql = "UPDATE grades SET courseName=?, score=?, status=? WHERE id=?";
        
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            
            pstmt.setString(1, grade.getCourseName());
            pstmt.setDouble(2, grade.getScore());
            pstmt.setString(3, grade.getStatus());
            pstmt.setInt(4, grade.getGradeId());
            
            pstmt.executeUpdate();
            System.out.println("✅ تم تعديل الدرجة بنجاح");
            return true;
            
        } catch (SQLException e) {
            System.out.println("❌ خطأ في التعديل: " + e.getMessage());
            return false;
        }
    }

    // حذف درجة
    public static boolean deleteGrade(int gradeId) {
        String sql = "DELETE FROM grades WHERE id=?";
        
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, gradeId);
            
            pstmt.executeUpdate();
            System.out.println("✅ تم حذف الدرجة بنجاح");
            return true;
            
        } catch (SQLException e) {
            System.out.println("❌ خطأ في الحذف: " + e.getMessage());
            return false;
        }
    }
}