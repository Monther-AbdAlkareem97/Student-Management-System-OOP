package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    
    // مسار ملف الداتابيس داخل المشروع
    private static final String URL = "jdbc:sqlite:school.db";
    private static Connection connection = null;

    // Singleton - اتصال واحد فقط
    public static Connection getConnection() {
        if (connection == null) {
            try {
                Class.forName("org.sqlite.JDBC");  // ⬅️ سطر جديد: تحميل الـ driver
                connection = DriverManager.getConnection(URL);
                System.out.println("✅ تم الاتصال بالداتابيس");
            } catch (ClassNotFoundException e) {
                System.out.println("❌ الـ Driver غير موجود: " + e.getMessage());
            } catch (SQLException e) {
                System.out.println("❌ فشل الاتصال: " + e.getMessage());
            }
        }
        return connection;
    }

    // إغلاق الاتصال
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
                System.out.println("✅ تم إغلاق الاتصال");
            } catch (SQLException e) {
                System.out.println("❌ خطأ: " + e.getMessage());
            }
        }
    }
}