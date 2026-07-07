package database;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;

public class DatabaseSetup {

    public static void createTables() {
        Connection conn = DatabaseConnection.getConnection();

        try {
            Statement stmt = conn.createStatement();

            // تفعيل Foreign Keys في SQLite
            stmt.execute("PRAGMA foreign_keys = ON");

            // جدول الصفوف الدراسية
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS classes (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    className TEXT NOT NULL UNIQUE,
                    level TEXT NOT NULL
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

            // جدول المواد (مرتبطة بصف + أستاذ)
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS courses (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    courseName TEXT NOT NULL,
                    classId INTEGER NOT NULL,
                    teacherId INTEGER,
                    credits INTEGER,
                    FOREIGN KEY (classId) REFERENCES classes(id),
                    FOREIGN KEY (teacherId) REFERENCES teachers(id)
                )
            """);

            // جدول الطلاب (مرتبطون بصف)
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS students (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT NOT NULL,
                    email TEXT,
                    password TEXT,
                    studentId TEXT UNIQUE,
                    classId INTEGER,
                    FOREIGN KEY (classId) REFERENCES classes(id)
                )
            """);

            // جدول الدرجات (طالب + مادة)
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS grades (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    studentId INTEGER NOT NULL,
                    courseId INTEGER NOT NULL,
                    score REAL,
                    status TEXT,
                    FOREIGN KEY (studentId) REFERENCES students(id),
                    FOREIGN KEY (courseId) REFERENCES courses(id)
                )
            """);

            System.out.println("✅ تم إنشاء الجداول");

        } catch (SQLException e) {
            System.out.println("❌ خطأ: " + e.getMessage());
        }
    }
}