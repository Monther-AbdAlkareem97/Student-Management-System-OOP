package controller;

import database.DatabaseConnection;
import model.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentController {

    // إضافة طالب جديد
    public static boolean addStudent(Student student) {
        String sql = "INSERT INTO students (name, email, password, studentId, classId) VALUES (?, ?, ?, ?, ?)";

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, student.getName());
            pstmt.setString(2, student.getEmail());
            pstmt.setString(3, student.getPassword());
            pstmt.setString(4, student.getStudentId());
            pstmt.setInt(5, student.getClassId());

            pstmt.executeUpdate();
            System.out.println("✅ تم إضافة الطالب بنجاح");
            return true;

        } catch (SQLException e) {
            System.out.println("❌ خطأ في الإضافة: " + e.getMessage());
            return false;
        }
    }

    // عرض كل الطلاب (مع اسم الصف)
    public static List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String sql = """
            SELECT s.id, s.name, s.email, s.password, s.studentId,
                   s.classId, c.className
            FROM students s
            LEFT JOIN classes c ON s.classId = c.id
        """;

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Student student = new Student(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getString("studentId"),
                    rs.getInt("classId"),
                    rs.getString("className")
                );
                students.add(student);
            }

        } catch (SQLException e) {
            System.out.println("❌ خطأ في العرض: " + e.getMessage());
        }

        return students;
    }

    // عرض طلاب صف معين
    public static List<Student> getStudentsByClassId(int classId) {
        List<Student> students = new ArrayList<>();
        String sql = """
            SELECT s.id, s.name, s.email, s.password, s.studentId,
                   s.classId, c.className
            FROM students s
            LEFT JOIN classes c ON s.classId = c.id
            WHERE s.classId = ?
        """;

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, classId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Student student = new Student(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getString("studentId"),
                    rs.getInt("classId"),
                    rs.getString("className")
                );
                students.add(student);
            }

        } catch (SQLException e) {
            System.out.println("❌ خطأ في العرض: " + e.getMessage());
        }

        return students;
    }

    // تعديل طالب
    public static boolean updateStudent(Student student) {
        String sql = "UPDATE students SET name=?, email=?, password=?, classId=? WHERE studentId=?";

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, student.getName());
            pstmt.setString(2, student.getEmail());
            pstmt.setString(3, student.getPassword());
            pstmt.setInt(4, student.getClassId());
            pstmt.setString(5, student.getStudentId());

            pstmt.executeUpdate();
            System.out.println("✅ تم تعديل الطالب بنجاح");
            return true;

        } catch (SQLException e) {
            System.out.println("❌ خطأ في التعديل: " + e.getMessage());
            return false;
        }
    }

    // حذف طالب
    public static boolean deleteStudent(String studentId) {
        String sql = "DELETE FROM students WHERE studentId=?";

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, studentId);

            pstmt.executeUpdate();
            System.out.println("✅ تم حذف الطالب بنجاح");
            return true;

        } catch (SQLException e) {
            System.out.println("❌ خطأ في الحذف: " + e.getMessage());
            return false;
        }
    }

    // البحث عن طالب بالـ studentId
    public static Student getStudentById(String studentId) {
        String sql = """
            SELECT s.id, s.name, s.email, s.password, s.studentId,
                   s.classId, c.className
            FROM students s
            LEFT JOIN classes c ON s.classId = c.id
            WHERE s.studentId = ?
        """;

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, studentId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Student(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getString("studentId"),
                    rs.getInt("classId"),
                    rs.getString("className")
                );
            }

        } catch (SQLException e) {
            System.out.println("❌ خطأ في البحث: " + e.getMessage());
        }

        return null;
    }
}