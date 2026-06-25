import database.DatabaseConnection;
import database.DatabaseSetup;
import controller.StudentController;
import model.Student;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        
        DatabaseConnection.getConnection();
        DatabaseSetup.createTables();
        
        // اختبار إضافة طالب
        Student s1 = new Student(0, "أحمد علي", "ahmed@test.com", "1234",
                                  "S001", "الأول", "A");
        StudentController.addStudent(s1);
        
        // اختبار عرض كل الطلاب
        List<Student> students = StudentController.getAllStudents();
        System.out.println("\n--- قائمة الطلاب ---");
        for (Student s : students) {
            System.out.println(s.getInfo());
        }
        
        DatabaseConnection.closeConnection();
    }
}