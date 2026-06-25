package controller;

import database.DatabaseConnection;
import model.Teacher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TeacherController {

    // إضافة أستاذ جديد
    public static boolean addTeacher(Teacher teacher) {
        String sql = "INSERT INTO teachers (name, email, password, teacherId, subject, salary, hoursWorked) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            
            pstmt.setString(1, teacher.getName());
            pstmt.setString(2, teacher.getEmail());
            pstmt.setString(3, teacher.getPassword());
            pstmt.setString(4, teacher.getTeacherId());
            pstmt.setString(5, teacher.getSubject());
            pstmt.setDouble(6, teacher.getSalary());
            pstmt.setDouble(7, teacher.getHoursWorked());
            
            pstmt.executeUpdate();
            System.out.println("✅ تم إضافة الأستاذ بنجاح");
            return true;
            
        } catch (SQLException e) {
            System.out.println("❌ خطأ في الإضافة: " + e.getMessage());
            return false;
        }
    }

    // عرض كل الأساتذة
    public static List<Teacher> getAllTeachers() {
        List<Teacher> teachers = new ArrayList<>();
        String sql = "SELECT * FROM teachers";
        
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Teacher t = new Teacher(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getString("teacherId"),
                    rs.getString("subject"),
                    rs.getDouble("salary"),
                    rs.getDouble("hoursWorked")
                );
                teachers.add(t);
            }
            
        } catch (SQLException e) {
            System.out.println("❌ خطأ في العرض: " + e.getMessage());
        }
        
        return teachers;
    }

    // تعديل أستاذ
    public static boolean updateTeacher(Teacher teacher) {
        String sql = "UPDATE teachers SET name=?, email=?, password=?, subject=?, salary=?, hoursWorked=? WHERE teacherId=?";
        
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            
            pstmt.setString(1, teacher.getName());
            pstmt.setString(2, teacher.getEmail());
            pstmt.setString(3, teacher.getPassword());
            pstmt.setString(4, teacher.getSubject());
            pstmt.setDouble(5, teacher.getSalary());
            pstmt.setDouble(6, teacher.getHoursWorked());
            pstmt.setString(7, teacher.getTeacherId());
            
            pstmt.executeUpdate();
            System.out.println("✅ تم تعديل الأستاذ بنجاح");
            return true;
            
        } catch (SQLException e) {
            System.out.println("❌ خطأ في التعديل: " + e.getMessage());
            return false;
        }
    }

    // حذف أستاذ
    public static boolean deleteTeacher(String teacherId) {
        String sql = "DELETE FROM teachers WHERE teacherId=?";
        
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, teacherId);
            
            pstmt.executeUpdate();
            System.out.println("✅ تم حذف الأستاذ بنجاح");
            return true;
            
        } catch (SQLException e) {
            System.out.println("❌ خطأ في الحذف: " + e.getMessage());
            return false;
        }
    }

    // البحث عن أستاذ بالـ teacherId
    public static Teacher getTeacherById(String teacherId) {
        String sql = "SELECT * FROM teachers WHERE teacherId=?";
        
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, teacherId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return new Teacher(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getString("teacherId"),
                    rs.getString("subject"),
                    rs.getDouble("salary"),
                    rs.getDouble("hoursWorked")
                );
            }
            
        } catch (SQLException e) {
            System.out.println("❌ خطأ في البحث: " + e.getMessage());
        }
        
        return null;
    }
}