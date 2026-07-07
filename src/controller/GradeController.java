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

    // إضافة درجة
    public static boolean addGrade(int studentId, int courseId, double score) {
        String sql = "INSERT INTO grades (studentId, courseId, score, status) VALUES (?, ?, ?, ?)";

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, studentId);
            pstmt.setInt(2, courseId);
            pstmt.setDouble(3, score);
            pstmt.setString(4, score >= 50 ? "ناجح" : "راسب");

            pstmt.executeUpdate();
            System.out.println("✅ تم إضافة الدرجة بنجاح");
            return true;

        } catch (SQLException e) {
            System.out.println("❌ خطأ في الإضافة: " + e.getMessage());
            return false;
        }
    }

    // عرض كل درجات طالب معين (مع اسم المادة)
    public static List<Grade> getGradesByStudentId(int studentId) {
        List<Grade> grades = new ArrayList<>();
        String sql = """
            SELECT g.id, g.studentId, g.courseId, c.courseName, g.score, g.status
            FROM grades g
            LEFT JOIN courses c ON g.courseId = c.id
            WHERE g.studentId = ?
        """;

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, studentId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Grade g = new Grade(
                    rs.getInt("id"),
                    rs.getInt("studentId"),
                    rs.getInt("courseId"),
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
    public static boolean updateGrade(int gradeId, double score) {
        String sql = "UPDATE grades SET score=?, status=? WHERE id=?";

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setDouble(1, score);
            pstmt.setString(2, score >= 50 ? "ناجح" : "راسب");
            pstmt.setInt(3, gradeId);

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