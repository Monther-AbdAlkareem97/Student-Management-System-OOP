package view;

import controller.ClassController;
import controller.StudentController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.SchoolClass;
import model.Student;

import java.util.List;

public class StudentView {

    private static TableView<Student> table         = new TableView<>();
    private static TextField nameField              = new TextField();
    private static TextField emailField             = new TextField();
    private static TextField passwordField          = new TextField();
    private static TextField studentIdField         = new TextField();
    private static ComboBox<SchoolClass> classCombo = new ComboBox<>();

    public static void show(Stage stage) {

        setupTable();
        loadStudents();
        loadClasses();

        // ===== Form =====
        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);
        form.setPadding(new Insets(10));
        form.getStyleClass().add("form-panel");

        nameField.getStyleClass().add("text-input");
        emailField.getStyleClass().add("text-input");
        passwordField.getStyleClass().add("text-input");
        studentIdField.getStyleClass().add("text-input");
        classCombo.getStyleClass().add("combo-box");

        form.addRow(0, new Label("الاسم:"), nameField);
        form.addRow(1, new Label("البريد:"), emailField);
        form.addRow(2, new Label("كلمة المرور:"), passwordField);
        form.addRow(3, new Label("رقم الطالب:"), studentIdField);
        form.addRow(4, new Label("الصف:"), classCombo);

        // ===== Buttons =====
        Button addBtn    = new Button("➕ إضافة");
        Button updateBtn = new Button("✏️ تعديل");
        Button deleteBtn = new Button("🗑️ حذف");
        Button clearBtn  = new Button("🔄 تفريغ");

        addBtn.getStyleClass().add("btn-primary");
        updateBtn.getStyleClass().add("btn-primary");
        deleteBtn.getStyleClass().add("btn-danger");
        clearBtn.getStyleClass().add("btn-secondary");

        addBtn.setOnAction(e    -> addStudent());
        updateBtn.setOnAction(e -> updateStudent());
        deleteBtn.setOnAction(e -> deleteStudent());
        clearBtn.setOnAction(e  -> clearFields());

        table.setOnMouseClicked(e -> fillFormFromSelection());

        HBox buttons = new HBox(10, addBtn, updateBtn, deleteBtn, clearBtn);
        buttons.setPadding(new Insets(10, 0, 0, 0));

        // ===== Main Content =====
        Label eyebrow = new Label("إدارة");
        Label title   = new Label("الطلاب");
        eyebrow.getStyleClass().add("page-eyebrow");
        title.getStyleClass().add("page-title");
        VBox header = new VBox(4, eyebrow, title);

        VBox tablePanel = new VBox(table);
        tablePanel.getStyleClass().add("panel");

        VBox mainContent = new VBox(20, header, tablePanel, form, buttons);
        mainContent.getStyleClass().add("main-content");
        HBox.setHgrow(mainContent, Priority.ALWAYS);

        // ===== Sidebar =====
        VBox sidebar = DashboardView.createSidebar(stage, "students");

        HBox root = new HBox(mainContent, sidebar);

        Scene scene = new Scene(root);
        scene.getStylesheets().add(
            StudentView.class.getResource("/styles.css").toExternalForm()
        );

        stage.setTitle("إدارة الطلاب");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.setWidth(1200);
        stage.setHeight(700);
        stage.show();
    }

    @SuppressWarnings("unchecked")
    private static void setupTable() {
        table.getColumns().clear();
        table.getStyleClass().add("table-view");

        TableColumn<Student, String> idCol = new TableColumn<>("رقم الطالب");
        idCol.setCellValueFactory(new PropertyValueFactory<>("studentId"));

        TableColumn<Student, String> nameCol = new TableColumn<>("الاسم");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Student, String> classCol = new TableColumn<>("الصف");
        classCol.setCellValueFactory(new PropertyValueFactory<>("className"));

        table.getColumns().addAll(idCol, nameCol, classCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    private static void loadStudents() {
        List<Student> students = StudentController.getAllStudents();
        ObservableList<Student> data = FXCollections.observableArrayList(students);
        table.setItems(data);
    }

    private static void loadClasses() {
        List<SchoolClass> classes = ClassController.getAllClasses();
        ObservableList<SchoolClass> data = FXCollections.observableArrayList(classes);
        classCombo.setItems(data);
    }

    private static void addStudent() {
        SchoolClass selectedClass = classCombo.getValue();
        if (selectedClass == null) {
            showError("اختر الصف أولاً");
            return;
        }
        Student s = new Student(0, nameField.getText(), emailField.getText(),
                passwordField.getText(), studentIdField.getText(),
                selectedClass.getId(), selectedClass.getClassName());
        StudentController.addStudent(s);
        loadStudents();
        clearFields();
    }

    private static void updateStudent() {
        SchoolClass selectedClass = classCombo.getValue();
        if (selectedClass == null) {
            showError("اختر الصف أولاً");
            return;
        }
        Student s = new Student(0, nameField.getText(), emailField.getText(),
                passwordField.getText(), studentIdField.getText(),
                selectedClass.getId(), selectedClass.getClassName());
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
            for (SchoolClass sc : classCombo.getItems()) {
                if (sc.getId() == selected.getClassId()) {
                    classCombo.setValue(sc);
                    break;
                }
            }
        }
    }

    private static void clearFields() {
        nameField.clear();
        emailField.clear();
        passwordField.clear();
        studentIdField.clear();
        classCombo.setValue(null);
    }

    private static void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("خطأ");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}