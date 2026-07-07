package controller;

import database.DatabaseConnection;
import model.SchoolClass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClassController {

    // إضافة صف جديد
    public static boolean addClass(SchoolClass schoolClass) {
        String sql = "INSERT INTO classes (className, level) VALUES (?, ?)";

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, schoolClass.getClassName());
            pstmt.setString(2, schoolClass.getLevel());

            pstmt.executeUpdate();
            System.out.println("✅ تم إضافة الصف بنجاح");
            return true;

        } catch (SQLException e) {
            System.out.println("❌ خطأ في الإضافة: " + e.getMessage());
            return false;
        }
    }

    // عرض كل الصفوف
    public static List<SchoolClass> getAllClasses() {
        List<SchoolClass> classes = new ArrayList<>();
        String sql = "SELECT * FROM classes";

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                SchoolClass sc = new SchoolClass(
                    rs.getInt("id"),
                    rs.getString("className"),
                    rs.getString("level")
                );
                classes.add(sc);
            }

        } catch (SQLException e) {
            System.out.println("❌ خطأ في العرض: " + e.getMessage());
        }

        return classes;
    }

    // تعديل صف
    public static boolean updateClass(SchoolClass schoolClass) {
        String sql = "UPDATE classes SET className=?, level=? WHERE id=?";

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, schoolClass.getClassName());
            pstmt.setString(2, schoolClass.getLevel());
            pstmt.setInt(3, schoolClass.getId());

            pstmt.executeUpdate();
            System.out.println("✅ تم تعديل الصف بنجاح");
            return true;

        } catch (SQLException e) {
            System.out.println("❌ خطأ في التعديل: " + e.getMessage());
            return false;
        }
    }

    // حذف صف
    public static boolean deleteClass(int classId) {
        String sql = "DELETE FROM classes WHERE id=?";

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, classId);

            pstmt.executeUpdate();
            System.out.println("✅ تم حذف الصف بنجاح");
            return true;

        } catch (SQLException e) {
            System.out.println("❌ خطأ في الحذف: " + e.getMessage());
            return false;
        }
    }
}