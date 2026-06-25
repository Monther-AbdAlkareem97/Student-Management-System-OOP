package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DashboardView {

    public static void show(Stage stage) {
        Label titleLabel = new Label("لوحة التحكم - نظام إدارة الطلاب");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Button studentsBtn = new Button("👥 إدارة الطلاب");
        Button teachersBtn = new Button("👨‍🏫 إدارة الأساتذة");
        Button coursesBtn = new Button("📚 إدارة المواد");
        Button gradesBtn = new Button("📝 إدارة الدرجات");
        Button reportsBtn = new Button("📊 التقارير");

        String btnStyle = "-fx-font-size: 14px; -fx-min-width: 200px; -fx-pref-height: 40px;";
        studentsBtn.setStyle(btnStyle);
        teachersBtn.setStyle(btnStyle);
        coursesBtn.setStyle(btnStyle);
        gradesBtn.setStyle(btnStyle);
        reportsBtn.setStyle(btnStyle);

        // مؤقتاً، رح نربط كل زر بشاشته لما نسويها
        studentsBtn.setOnAction(e -> {
            StudentView.show(stage);
        });
        
        teachersBtn.setOnAction(e -> {
            TeacherView.show(stage);
        });
        
        coursesBtn.setOnAction(e -> {
            CourseView.show(stage);
        });
        
        gradesBtn.setOnAction(e -> {
            GradeView.show(stage);
        });
        
        reportsBtn.setOnAction(e -> {
            ReportsView.show(stage);
        });

        VBox layout = new VBox(15);
        layout.setPadding(new Insets(40));
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(titleLabel, studentsBtn, teachersBtn, coursesBtn, gradesBtn, reportsBtn);

        Scene scene = new Scene(layout, 450, 400);
        stage.setTitle("لوحة التحكم");
        stage.setScene(scene);
        stage.show();
    }
}