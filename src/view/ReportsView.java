package view;

import controller.CourseController;
import controller.GradeController;
import controller.StudentController;
import controller.TeacherController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Grade;
import model.Student;

import java.util.List;

public class ReportsView {

    public static void show(Stage stage) {

        VBox content = new VBox(15);
        content.setPadding(new Insets(20));

        Label title = new Label("📊 التقارير والإحصائيات");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        // إحصائيات عامة
        List<Student> students = StudentController.getAllStudents();
        int teacherCount = TeacherController.getAllTeachers().size();
        int courseCount = CourseController.getAllCourses().size();

        Label generalStats = new Label(
                "عدد الطلاب: " + students.size() +
                " | عدد الأساتذة: " + teacherCount +
                " | عدد المواد: " + courseCount
        );
        generalStats.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        // الناجحين والراسبين
        VBox passFailBox = new VBox(5);
        Label passFailTitle = new Label("✅ الناجحون / ❌ الراسبون:");
        passFailTitle.setStyle("-fx-font-weight: bold;");
        passFailBox.getChildren().add(passFailTitle);

        double highestScore = -1;
        double lowestScore = 101;
        String highestInfo = "";
        String lowestInfo = "";

        for (Student s : students) {
            List<Grade> grades = GradeController.getGradesByStudentId(s.getId());
            double gpa = GradeController.calculateGPA(s.getId());

            String status = gpa >= 50 ? "✅ ناجح" : "❌ راسب";
            if (!grades.isEmpty()) {
                passFailBox.getChildren().add(new Label(
                        s.getName() + " (" + s.getStudentId() + ") - المعدل: " +
                        String.format("%.2f", gpa) + " - " + status
                ));
            }

            for (Grade g : grades) {
                if (g.getScore() > highestScore) {
                    highestScore = g.getScore();
                    highestInfo = s.getName() + " - " + g.getCourseName() + " (" + g.getScore() + ")";
                }
                if (g.getScore() < lowestScore) {
                    lowestScore = g.getScore();
                    lowestInfo = s.getName() + " - " + g.getCourseName() + " (" + g.getScore() + ")";
                }
            }
        }

        // أعلى وأقل درجة
        VBox highLowBox = new VBox(5);
        Label highLowTitle = new Label("🏆 أعلى وأقل درجة:");
        highLowTitle.setStyle("-fx-font-weight: bold;");
        highLowBox.getChildren().add(highLowTitle);

        if (highestScore != -1) {
            highLowBox.getChildren().add(new Label("أعلى درجة: " + highestInfo));
            highLowBox.getChildren().add(new Label("أقل درجة: " + lowestInfo));
        } else {
            highLowBox.getChildren().add(new Label("لا توجد درجات مسجلة بعد"));
        }

        Button backBtn = new Button("⬅️ رجوع");
        backBtn.setOnAction(e -> DashboardView.show(stage));

        content.getChildren().addAll(title, generalStats, passFailBox, highLowBox, backBtn);

        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true);

        Scene scene = new Scene(scrollPane, 550, 600);
        stage.setTitle("التقارير");
        stage.setScene(scene);
        stage.show();
    }
}