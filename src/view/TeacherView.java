package view;

import controller.TeacherController;
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
import model.Teacher;

import java.util.List;

public class TeacherView {

    private static TableView<Teacher> table = new TableView<>();
    private static TextField nameField = new TextField();
    private static TextField emailField = new TextField();
    private static TextField passwordField = new TextField();
    private static TextField teacherIdField = new TextField();
    private static TextField subjectField = new TextField();
    private static TextField salaryField = new TextField();
    private static TextField hoursField = new TextField();

    public static void show(Stage stage) {

        setupTable();
        loadTeachers();

        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);
        form.setPadding(new Insets(10));

        form.addRow(0, new Label("الاسم:"), nameField);
        form.addRow(1, new Label("البريد:"), emailField);
        form.addRow(2, new Label("كلمة المرور:"), passwordField);
        form.addRow(3, new Label("رقم الأستاذ:"), teacherIdField);
        form.addRow(4, new Label("المادة:"), subjectField);
        form.addRow(5, new Label("الراتب الأساسي:"), salaryField);
        form.addRow(6, new Label("ساعات العمل:"), hoursField);

        Button addBtn = new Button("➕ إضافة");
        Button updateBtn = new Button("✏️ تعديل");
        Button deleteBtn = new Button("🗑️ حذف");
        Button clearBtn = new Button("🔄 تفريغ");
        Button backBtn = new Button("⬅️ رجوع");

        addBtn.setOnAction(e -> addTeacher());
        updateBtn.setOnAction(e -> updateTeacher());
        deleteBtn.setOnAction(e -> deleteTeacher());
        clearBtn.setOnAction(e -> clearFields());
        backBtn.setOnAction(e -> DashboardView.show(stage));

        table.setOnMouseClicked(e -> fillFormFromSelection());

        HBox buttons = new HBox(10, addBtn, updateBtn, deleteBtn, clearBtn, backBtn);

        VBox layout = new VBox(15, new Label("إدارة الأساتذة"), table, form, buttons);
        layout.setPadding(new Insets(20));

        Scene scene = new Scene(layout, 650, 650);
        stage.setTitle("إدارة الأساتذة");
        stage.setScene(scene);
        stage.show();
    }

    @SuppressWarnings("unchecked")
    private static void setupTable() {
        table.getColumns().clear();

        TableColumn<Teacher, String> idCol = new TableColumn<>("رقم الأستاذ");
        idCol.setCellValueFactory(new PropertyValueFactory<>("teacherId"));

        TableColumn<Teacher, String> nameCol = new TableColumn<>("الاسم");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Teacher, String> subjectCol = new TableColumn<>("المادة");
        subjectCol.setCellValueFactory(new PropertyValueFactory<>("subject"));

        TableColumn<Teacher, Double> salaryCol = new TableColumn<>("الراتب الأساسي");
        salaryCol.setCellValueFactory(new PropertyValueFactory<>("salary"));

        table.getColumns().addAll(idCol, nameCol, subjectCol, salaryCol);
    }

    private static void loadTeachers() {
        List<Teacher> teachers = TeacherController.getAllTeachers();
        ObservableList<Teacher> data = FXCollections.observableArrayList(teachers);
        table.setItems(data);
    }

    private static void addTeacher() {
        try {
            Teacher t = new Teacher(0, nameField.getText(), emailField.getText(),
                    passwordField.getText(), teacherIdField.getText(),
                    subjectField.getText(),
                    Double.parseDouble(salaryField.getText()),
                    Double.parseDouble(hoursField.getText()));

            TeacherController.addTeacher(t);
            loadTeachers();
            clearFields();
        } catch (NumberFormatException ex) {
            showError("تأكد من إدخال الراتب وساعات العمل كأرقام صحيحة");
        }
    }

    private static void updateTeacher() {
        try {
            Teacher t = new Teacher(0, nameField.getText(), emailField.getText(),
                    passwordField.getText(), teacherIdField.getText(),
                    subjectField.getText(),
                    Double.parseDouble(salaryField.getText()),
                    Double.parseDouble(hoursField.getText()));

            TeacherController.updateTeacher(t);
            loadTeachers();
            clearFields();
        } catch (NumberFormatException ex) {
            showError("تأكد من إدخال الراتب وساعات العمل كأرقام صحيحة");
        }
    }

    private static void deleteTeacher() {
        TeacherController.deleteTeacher(teacherIdField.getText());
        loadTeachers();
        clearFields();
    }

    private static void fillFormFromSelection() {
        Teacher selected = table.getSelectionModel().getSelectedItem();
        if (selected != null) {
            nameField.setText(selected.getName());
            emailField.setText(selected.getEmail());
            passwordField.setText(selected.getPassword());
            teacherIdField.setText(selected.getTeacherId());
            subjectField.setText(selected.getSubject());
            salaryField.setText(String.valueOf(selected.getSalary()));
            hoursField.setText(String.valueOf(selected.getHoursWorked()));
        }
    }

    private static void clearFields() {
        nameField.clear();
        emailField.clear();
        passwordField.clear();
        teacherIdField.clear();
        subjectField.clear();
        salaryField.clear();
        hoursField.clear();
    }

    private static void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("خطأ");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}