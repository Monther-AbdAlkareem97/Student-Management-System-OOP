package view;

import controller.GradeController;
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
import model.Grade;
import model.Student;

import java.util.List;

public class GradeView {

    private static TableView<Grade> table = new TableView<>();
    private static ComboBox<Student> studentCombo = new ComboBox<>();
    private static TextField courseNameField = new TextField();
    private static TextField scoreField = new TextField();
    private static Label gpaLabel = new Label();

    public static void show(Stage stage) {

        setupTable();
        loadStudents();

        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);
        form.setPadding(new Insets(10));

        form.addRow(0, new Label("الطالب:"), studentCombo);
        form.addRow(1, new Label("اسم المادة:"), courseNameField);
        form.addRow(2, new Label("الدرجة:"), scoreField);

        // لما نختار طالب من الـ ComboBox، نعرض درجاته في الجدول
        studentCombo.setOnAction(e -> loadGradesForSelectedStudent());

        Button addBtn = new Button("➕ إضافة درجة");
        Button deleteBtn = new Button("🗑️ حذف درجة");
        Button clearBtn = new Button("🔄 تفريغ");
        Button backBtn = new Button("⬅️ رجوع");

        addBtn.setOnAction(e -> addGrade());
        deleteBtn.setOnAction(e -> deleteGrade());
        clearBtn.setOnAction(e -> clearFields());
        backBtn.setOnAction(e -> DashboardView.show(stage));

        HBox buttons = new HBox(10, addBtn, deleteBtn, clearBtn, backBtn);

        VBox layout = new VBox(15, new Label("إدارة الدرجات"), form, table, gpaLabel, buttons);
        layout.setPadding(new Insets(20));

        Scene scene = new Scene(layout, 600, 600);
        stage.setTitle("إدارة الدرجات");
        stage.setScene(scene);
        stage.show();
    }

    @SuppressWarnings("unchecked")
    private static void setupTable() {
        table.getColumns().clear();

        TableColumn<Grade, String> courseCol = new TableColumn<>("اسم المادة");
        courseCol.setCellValueFactory(new PropertyValueFactory<>("courseName"));

        TableColumn<Grade, Double> scoreCol = new TableColumn<>("الدرجة");
        scoreCol.setCellValueFactory(new PropertyValueFactory<>("score"));

        TableColumn<Grade, String> statusCol = new TableColumn<>("الحالة");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        table.getColumns().addAll(courseCol, scoreCol, statusCol);
    }

    private static void loadStudents() {
        List<Student> students = StudentController.getAllStudents();
        ObservableList<Student> data = FXCollections.observableArrayList(students);
        studentCombo.setItems(data);

        // نعرض اسم الطالب + رقمه بدل ما يطبع object غريب
        studentCombo.setCellFactory(lv -> new javafx.scene.control.ListCell<Student>() {
            @Override
            protected void updateItem(Student item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getStudentId() + " - " + item.getName());
            }
        });
        studentCombo.setButtonCell(studentCombo.getCellFactory().call(null));
    }

    private static void loadGradesForSelectedStudent() {
        Student selected = studentCombo.getValue();
        if (selected == null) return;

        List<Grade> grades = GradeController.getGradesByStudentId(selected.getId());
        ObservableList<Grade> data = FXCollections.observableArrayList(grades);
        table.setItems(data);

        double gpa = GradeController.calculateGPA(selected.getId());
        gpaLabel.setText("المعدل التراكمي (GPA): " + String.format("%.2f", gpa));
    }

    private static void addGrade() {
        Student selected = studentCombo.getValue();
        if (selected == null) {
            showError("اختر طالب أولاً");
            return;
        }

        try {
            double score = Double.parseDouble(scoreField.getText());
            Grade grade = new Grade(0, courseNameField.getText(), score);

            GradeController.addGrade(selected.getId(), grade);
            loadGradesForSelectedStudent();
            clearFields();
        } catch (NumberFormatException ex) {
            showError("تأكد من إدخال الدرجة كرقم صحيح");
        }
    }

    private static void deleteGrade() {
        Grade selectedGrade = table.getSelectionModel().getSelectedItem();
        if (selectedGrade == null) {
            showError("اختر درجة من الجدول للحذف");
            return;
        }

        GradeController.deleteGrade(selectedGrade.getGradeId());
        loadGradesForSelectedStudent();
    }

    private static void clearFields() {
        courseNameField.clear();
        scoreField.clear();
    }

    private static void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("خطأ");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}