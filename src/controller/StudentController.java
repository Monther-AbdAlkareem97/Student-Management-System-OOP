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
        String sql = "INSERT INTO students (name, email, password, studentId, level, className) VALUES (?, ?, ?, ?, ?, ?)";
        
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            
            pstmt.setString(1, student.getName());
            pstmt.setString(2, student.getEmail());
            pstmt.setString(3, student.getPassword());
            pstmt.setString(4, student.getStudentId());
            pstmt.setString(5, student.getLevel());
            pstmt.setString(6, student.getClassName());
            
            pstmt.executeUpdate();
            System.out.println("✅ تم إضافة الطالب بنجاح");
            return true;
            
        } catch (SQLException e) {
            System.out.println("❌ خطأ في الإضافة: " + e.getMessage());
            return false;
        }
    }

    // عرض كل الطلاب
    public static List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students";
        
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Student s = new Student(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getString("studentId"),
                    rs.getString("level"),
                    rs.getString("className")
                );
                students.add(s);
            }
            
        } catch (SQLException e) {
            System.out.println("❌ خطأ في العرض: " + e.getMessage());
        }
        
        return students;
    }

    // تعديل طالب
    public static boolean updateStudent(Student student) {
        String sql = "UPDATE students SET name=?, email=?, password=?, level=?, className=? WHERE studentId=?";
        
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            
            pstmt.setString(1, student.getName());
            pstmt.setString(2, student.getEmail());
            pstmt.setString(3, student.getPassword());
            pstmt.setString(4, student.getLevel());
            pstmt.setString(5, student.getClassName());
            pstmt.setString(6, student.getStudentId());
            
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
        String sql = "SELECT * FROM students WHERE studentId=?";
        
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
                    rs.getString("level"),
                    rs.getString("className")
                );
            }
            
        } catch (SQLException e) {
            System.out.println("❌ خطأ في البحث: " + e.getMessage());
        }
        
        return null;
    }
}