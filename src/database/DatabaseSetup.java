package database;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;

public class DatabaseSetup {

    public static void createTables() {
        Connection conn = DatabaseConnection.getConnection();
        
        try {
            Statement stmt = conn.createStatement();

            // جدول الطلاب
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS students (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT NOT NULL,
                    email TEXT,
                    password TEXT,
                    studentId TEXT UNIQUE,
                    level TEXT,
                    className TEXT
                )
            """);

            // جدول الأساتذة
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS teachers (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT NOT NULL,
                    email TEXT,
                    password TEXT,
                    teacherId TEXT UNIQUE,
                    subject TEXT,
                    salary REAL,
                    hoursWorked REAL
                )
            """);

            // جدول المواد
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS courses (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    courseName TEXT NOT NULL,
                    teacherName TEXT,
                    credits INTEGER
                )
            """);

            // جدول الدرجات
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS grades (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    studentId INTEGER,
                    courseName TEXT,
                    score REAL,
                    status TEXT,
                    FOREIGN KEY (studentId) REFERENCES students(id)
                )
            """);

            System.out.println("✅ تم إنشاء الجداول");

        } catch (SQLException e) {
            System.out.println("❌ خطأ: " + e.getMessage());
        }
    }
}