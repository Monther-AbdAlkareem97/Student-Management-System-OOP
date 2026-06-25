package view;

import controller.StudentController;
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
import model.Student;

import java.util.List;

public class StudentView {

    private static TableView<Student> table = new TableView<>();
    private static TextField nameField = new TextField();
    private static TextField emailField = new TextField();
    private static TextField passwordField = new TextField();
    private static TextField studentIdField = new TextField();
    private static TextField levelField = new TextField();
    private static TextField classNameField = new TextField();

    public static void show(Stage stage) {

        // إعداد الجدول
        setupTable();
        loadStudents();

        // فورم الإدخال
        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);
        form.setPadding(new Insets(10));

        form.addRow(0, new Label("الاسم:"), nameField);
        form.addRow(1, new Label("البريد:"), emailField);
        form.addRow(2, new Label("كلمة المرور:"), passwordField);
        form.addRow(3, new Label("رقم الطالب:"), studentIdField);
        form.addRow(4, new Label("المرحلة:"), levelField);
        form.addRow(5, new Label("الصف:"), classNameField);

        // الأزرار
        Button addBtn = new Button("➕ إضافة");
        Button updateBtn = new Button("✏️ تعديل");
        Button deleteBtn = new Button("🗑️ حذف");
        Button clearBtn = new Button("🔄 تفريغ");
        Button backBtn = new Button("⬅️ رجوع");

        addBtn.setOnAction(e -> addStudent());
        updateBtn.setOnAction(e -> updateStudent());
        deleteBtn.setOnAction(e -> deleteStudent());
        clearBtn.setOnAction(e -> clearFields());
        backBtn.setOnAction(e -> DashboardView.show(stage));

        // لما تنضغط صف بالجدول، يتعبى الفورم
        table.setOnMouseClicked(e -> fillFormFromSelection());

        HBox buttons = new HBox(10, addBtn, updateBtn, deleteBtn, clearBtn, backBtn);

        VBox layout = new VBox(15, new Label("إدارة الطلاب"), table, form, buttons);
        layout.setPadding(new Insets(20));

        Scene scene = new Scene(layout, 650, 600);
        stage.setTitle("إدارة الطلاب");
        stage.setScene(scene);
        stage.show();
    }

    @SuppressWarnings("unchecked")
    private static void setupTable() {
        table.getColumns().clear();

        TableColumn<Student, String> idCol = new TableColumn<>("رقم الطالب");
        idCol.setCellValueFactory(new PropertyValueFactory<>("studentId"));

        TableColumn<Student, String> nameCol = new TableColumn<>("الاسم");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Student, String> levelCol = new TableColumn<>("المرحلة");
        levelCol.setCellValueFactory(new PropertyValueFactory<>("level"));

        TableColumn<Student, String> classCol = new TableColumn<>("الصف");
        classCol.setCellValueFactory(new PropertyValueFactory<>("className"));

        table.getColumns().addAll(idCol, nameCol, levelCol, classCol);
    }

    private static void loadStudents() {
        List<Student> students = StudentController.getAllStudents();
        ObservableList<Student> data = FXCollections.observableArrayList(students);
        table.setItems(data);
    }

    private static void addStudent() {
        Student s = new Student(0, nameField.getText(), emailField.getText(),
                passwordField.getText(), studentIdField.getText(),
                levelField.getText(), classNameField.getText());

        StudentController.addStudent(s);
        loadStudents();
        clearFields();
    }

    private static void updateStudent() {
        Student s = new Student(0, nameField.getText(), emailField.getText(),
                passwordField.getText(), studentIdField.getText(),
                levelField.getText(), classNameField.getText());

        StudentController.updateStudent(s);
        loadStudents();
        clearFields();
    }

    private static void deleteStudent() {
        StudentController.deleteStudent(studentIdField.getText());
        loadStudents();
        clearFields();
    }

    private static void fillFormFromSelection() {
        Student selected = table.getSelectionModel().getSelectedItem();
        if (selected != null) {
            nameField.setText(selected.getName());
            emailField.setText(selected.getEmail());
            passwordField.setText(selected.getPassword());
            studentIdField.setText(selected.getStudentId());
            levelField.setText(selected.getLevel());
            classNameField.setText(selected.getClassName());
        }
    }

    private static void clearFields() {
        nameField.clear();
        emailField.clear();
        passwordField.clear();
        studentIdField.clear();
        levelField.clear();
        classNameField.clear();
    }
}