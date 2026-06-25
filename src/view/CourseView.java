package view;

import controller.CourseController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Course;

import java.util.List;

public class CourseView {

    private static TableView<Course> table = new TableView<>();
    private static TextField courseNameField = new TextField();
    private static TextField teacherNameField = new TextField();
    private static TextField creditsField = new TextField();
    private static TextField courseIdField = new TextField(); // مخفي، نحتاجه للتعديل والحذف

    public static void show(Stage stage) {

        setupTable();
        loadCourses();

        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);
        form.setPadding(new Insets(10));

        form.addRow(0, new Label("اسم المادة:"), courseNameField);
        form.addRow(1, new Label("اسم الأستاذ:"), teacherNameField);
        form.addRow(2, new Label("عدد الساعات:"), creditsField);

        Button addBtn = new Button("➕ إضافة");
        Button updateBtn = new Button("✏️ تعديل");
        Button deleteBtn = new Button("🗑️ حذف");
        Button clearBtn = new Button("🔄 تفريغ");
        Button backBtn = new Button("⬅️ رجوع");

        addBtn.setOnAction(e -> addCourse());
        updateBtn.setOnAction(e -> updateCourse());
        deleteBtn.setOnAction(e -> deleteCourse());
        clearBtn.setOnAction(e -> clearFields());
        backBtn.setOnAction(e -> DashboardView.show(stage));

        table.setOnMouseClicked(e -> fillFormFromSelection());

        HBox buttons = new HBox(10, addBtn, updateBtn, deleteBtn, clearBtn, backBtn);

        VBox layout = new VBox(15, new Label("إدارة المواد"), table, form, buttons);
        layout.setPadding(new Insets(20));

        Scene scene = new Scene(layout, 600, 550);
        stage.setTitle("إدارة المواد");
        stage.setScene(scene);
        stage.show();
    }

    @SuppressWarnings("unchecked")
    private static void setupTable() {
        table.getColumns().clear();

        TableColumn<Course, Integer> idCol = new TableColumn<>("الرقم");
        idCol.setCellValueFactory(new PropertyValueFactory<>("courseId"));

        TableColumn<Course, String> nameCol = new TableColumn<>("اسم المادة");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("courseName"));

        TableColumn<Course, String> teacherCol = new TableColumn<>("الأستاذ");
        teacherCol.setCellValueFactory(new PropertyValueFactory<>("teacherName"));

        TableColumn<Course, Integer> creditsCol = new TableColumn<>("الساعات");
        creditsCol.setCellValueFactory(new PropertyValueFactory<>("credits"));

        table.getColumns().addAll(idCol, nameCol, teacherCol, creditsCol);
    }

    private static void loadCourses() {
        List<Course> courses = CourseController.getAllCourses();
        ObservableList<Course> data = FXCollections.observableArrayList(courses);
        table.setItems(data);
    }

    private static void addCourse() {
        try {
            Course c = new Course(0, courseNameField.getText(), teacherNameField.getText(),
                    Integer.parseInt(creditsField.getText()));

            CourseController.addCourse(c);
            loadCourses();
            clearFields();
        } catch (NumberFormatException ex) {
            showError("تأكد من إدخال عدد الساعات كرقم صحيح");
        }
    }

    private static void updateCourse() {
        try {
            Course selected = table.getSelectionModel().getSelectedItem();
            if (selected == null) {
                showError("اختر مادة من الجدول أولاً للتعديل");
                return;
            }

            Course c = new Course(selected.getCourseId(), courseNameField.getText(),
                    teacherNameField.getText(), Integer.parseInt(creditsField.getText()));

            CourseController.updateCourse(c);
            loadCourses();
            clearFields();
        } catch (NumberFormatException ex) {
            showError("تأكد من إدخال عدد الساعات كرقم صحيح");
        }
    }

    private static void deleteCourse() {
        Course selected = table.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showError("اختر مادة من الجدول أولاً للحذف");
            return;
        }
        CourseController.deleteCourse(selected.getCourseId());
        loadCourses();
        clearFields();
    }

    private static void fillFormFromSelection() {
        Course selected = table.getSelectionModel().getSelectedItem();
        if (selected != null) {
            courseNameField.setText(selected.getCourseName());
            teacherNameField.setText(selected.getTeacherName());
            creditsField.setText(String.valueOf(selected.getCredits()));
        }
    }

    private static void clearFields() {
        courseNameField.clear();
        teacherNameField.clear();
        creditsField.clear();
    }

    private static void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("خطأ");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}