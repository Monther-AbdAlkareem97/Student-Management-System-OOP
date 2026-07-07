package view;

import controller.CourseController;
import controller.StudentController;
import controller.TeacherController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class DashboardView {

    public static void show(Stage stage) {

        // ===== Sidebar =====
        VBox sidebar = createSidebar(stage, "dashboard");

        // ===== Main Content =====
        VBox mainContent = new VBox(20);
        mainContent.getStyleClass().add("main-content");
        VBox.setVgrow(mainContent, Priority.ALWAYS);
        HBox.setHgrow(mainContent, Priority.ALWAYS);

        // Header
        Label eyebrow = new Label("لوحة التحكم");
        Label title   = new Label("نظرة عامة على النظام");
        eyebrow.getStyleClass().add("page-eyebrow");
        title.getStyleClass().add("page-title");
        VBox header = new VBox(4, eyebrow, title);

        // Stat Cards
        int studentCount = StudentController.getAllStudents().size();
        int teacherCount = TeacherController.getAllTeachers().size();
        int courseCount  = CourseController.getAllCourses().size();

        HBox statsRow = new HBox(14,
            createStatCard(String.valueOf(studentCount), "إجمالي الطلاب"),
            createStatCard(String.valueOf(teacherCount), "إجمالي الأساتذة"),
            createStatCard(String.valueOf(courseCount),  "المواد الدراسية")
        );

        mainContent.getChildren().addAll(header, statsRow);

        // ===== Root =====
        HBox root = new HBox(mainContent, sidebar);
        HBox.setHgrow(mainContent, Priority.ALWAYS);

        Scene scene = new Scene(root);
        scene.getStylesheets().add(
            DashboardView.class.getResource("/styles.css").toExternalForm()
        );

        stage.setTitle("لوحة التحكم - نظام إدارة الطلاب");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.setWidth(1200);
        stage.setHeight(700);
        stage.show();
    }

    // ===== Sidebar مشترك بين كل الشاشات =====
    public static VBox createSidebar(Stage stage, String activePage) {
        VBox sidebar = new VBox();
        sidebar.getStyleClass().add("sidebar");

        // Brand
        Label brandTitle = new Label("نظام إدارة الطلاب");
        brandTitle.getStyleClass().add("sidebar-brand-title");
        VBox brand = new VBox(brandTitle);
        brand.getStyleClass().add("sidebar-brand");
        brand.setPadding(new Insets(20));

        // Nav Buttons
        Button dashBtn     = createNavBtn("🏠  الرئيسية",      "dashboard", activePage, e -> DashboardView.show(stage));
        Button classesBtn  = createNavBtn("🏫  إدارة الصفوف",  "classes",   activePage, e -> ClassView.show(stage));
        Button studentsBtn = createNavBtn("👥  إدارة الطلاب",  "students",  activePage, e -> StudentView.show(stage));
        Button teachersBtn = createNavBtn("👨‍🏫  إدارة الأساتذة","teachers",  activePage, e -> TeacherView.show(stage));
        Button coursesBtn  = createNavBtn("📚  إدارة المواد",  "courses",   activePage, e -> CourseView.show(stage));
        Button gradesBtn   = createNavBtn("📝  إدارة الدرجات", "grades",    activePage, e -> GradeView.show(stage));
        Button reportsBtn  = createNavBtn("📊  التقارير",      "reports",   activePage, e -> ReportsView.show(stage));

        // Logout
        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        Button logoutBtn = new Button("🚪  تسجيل الخروج");
        logoutBtn.getStyleClass().add("logout-btn");
        logoutBtn.setOnAction(e -> LoginView.show(stage));

        sidebar.getChildren().addAll(
            brand,
            dashBtn, classesBtn, studentsBtn,
            teachersBtn, coursesBtn, gradesBtn, reportsBtn,
            spacer, logoutBtn
        );

        return sidebar;
    }

    // ===== مساعد: إنشاء زر Nav =====
    private static Button createNavBtn(String text, String page, String activePage,
                                       javafx.event.EventHandler<javafx.event.ActionEvent> action) {
        Button btn = new Button(text);
        btn.getStyleClass().add(page.equals(activePage) ? "nav-btn-active" : "nav-btn");
        btn.setOnAction(action);
        btn.setMaxWidth(Double.MAX_VALUE);
        return btn;
    }

    // ===== مساعد: إنشاء Stat Card =====
    private static VBox createStatCard(String number, String label) {
        Label numLabel = new Label(number);
        Label lblLabel = new Label(label);
        numLabel.getStyleClass().add("stat-number");
        lblLabel.getStyleClass().add("stat-label");

        VBox card = new VBox(6, numLabel, lblLabel);
        card.getStyleClass().add("stat-card");
        card.setPrefWidth(160);
        return card;
    }
}