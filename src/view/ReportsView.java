package view;

import controller.ClassController;
import controller.CourseController;
import controller.GradeController;
import controller.StudentController;
import controller.TeacherController;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import model.Course;
import model.Grade;
import model.SchoolClass;
import model.Student;

import java.util.List;

public class ReportsView {


    private static ComboBox<String> levelCombo = new ComboBox<>();
    private static ComboBox<SchoolClass> classCombo = new ComboBox<>();

    private static TableView<Student> studentsTable = new TableView<>();

    private static TableView<Grade> gradesTable = new TableView<>();

    private static Label gpaLabel =
            new Label("المعدل التراكمي: -");



    public static void show(Stage stage) {


        setupStudentsTable();
        setupGradesTable();


        loadLevels();



        // عند اختيار المرحلة
        levelCombo.setOnAction(e -> {

            loadClassesByLevel();

            studentsTable.getItems().clear();
            gradesTable.getItems().clear();

            gpaLabel.setText(
                    "المعدل التراكمي: -"
            );

        });



        // عند اختيار السنة
        classCombo.setOnAction(e -> {

            loadStudentsByClass();

            gradesTable.getItems().clear();

            gpaLabel.setText(
                    "المعدل التراكمي: -"
            );

        });



        studentsTable.setOnMouseClicked(e -> {

            loadGradesForStudent();

        });



        // ===== Filters =====


        GridPane filterPanel =
                new GridPane();


        filterPanel.setHgap(15);
        filterPanel.setVgap(10);
        filterPanel.setPadding(
                new Insets(20)
        );


        filterPanel.getStyleClass()
                .add("form-panel");



        levelCombo.getStyleClass()
                .add("combo-box");


        classCombo.getStyleClass()
                .add("combo-box");



        filterPanel.addRow(
                0,
                new Label("المرحلة:"),
                levelCombo
        );


        filterPanel.addRow(
                1,
                new Label("السنة:"),
                classCombo
        );




        // ===== Students Panel =====


        VBox studentsPanel =
                new VBox(
                        studentsTable
                );


        studentsPanel.getStyleClass()
                .add("panel");




        // ===== Grades Panel =====


        VBox gradesPanel =
                new VBox(
                        gradesTable,
                        gpaLabel
                );


        gradesPanel.setSpacing(10);


        gradesPanel.setPadding(
                new Insets(10)
        );


        gradesPanel.getStyleClass()
                .add("panel");



        gpaLabel.getStyleClass()
                .add("panel-title");




        // ===== Header =====


        Label eyebrow =
                new Label("إدارة");


        Label title =
                new Label("التقارير الدراسية");


        eyebrow.getStyleClass()
                .add("page-eyebrow");


        title.getStyleClass()
                .add("page-title");



        VBox header =
                new VBox(
                        4,
                        eyebrow,
                        title
                );





        // ===== Main Content =====


        VBox mainContent =
                new VBox(
                        20,
                        header,
                        filterPanel,
                        studentsPanel,
                        gradesPanel
                );


        mainContent.getStyleClass()
                .add("main-content");



        HBox.setHgrow(
                mainContent,
                Priority.ALWAYS
        );



        VBox sidebar =
                DashboardView.createSidebar(
                        stage,
                        "reports"
                );



        HBox root =
                new HBox(
                        mainContent,
                        sidebar
                );



        Scene scene =
                new Scene(root);



        scene.getStylesheets()
                .add(
                    ReportsView.class
                    .getResource("/styles.css")
                    .toExternalForm()
                );



        stage.setTitle(
                "التقارير"
        );


        stage.setScene(scene);

        stage.setMaximized(true);

        stage.show();

    }




    // تحميل المراحل

    private static void loadLevels() {


        levelCombo.setItems(
                FXCollections.observableArrayList(
                        "ابتدائي",
                        "إعدادي",
                        "ثانوي"
                )
        );

    }





    // تحميل السنوات حسب المرحلة

    private static void loadClassesByLevel() {


        String level =
                levelCombo.getValue();



        if(level == null)
            return;



        List<SchoolClass> classes =
                ClassController.getAllClasses();



        classes.removeIf(
                c -> !c.getLevel().equals(level)
        );



        classCombo.setItems(
                FXCollections.observableArrayList(
                        classes
                )
        );


    }





    // تحميل الطلاب

    private static void loadStudentsByClass() {


        SchoolClass selected =
                classCombo.getValue();



        if(selected == null)
            return;



        studentsTable.setItems(
                FXCollections.observableArrayList(
                        StudentController
                        .getStudentsByClassId(
                                selected.getId()
                        )
                )
        );


    }






    // تحميل درجات الطالب

    private static void loadGradesForStudent() {


        Student student =
                studentsTable
                .getSelectionModel()
                .getSelectedItem();



        if(student == null)
            return;



        List<Grade> grades =
                GradeController
                .getGradesByStudentId(
                        student.getId()
                );



        gradesTable.setItems(
                FXCollections.observableArrayList(
                        grades
                )
        );



        double gpa =
                GradeController
                .calculateGPA(
                        student.getId()
                );



        gpaLabel.setText(
                "الطالب: "
                + student.getName()
                + " | GPA: "
                + String.format(
                        "%.2f",
                        gpa
                )
        );


    }







    private static void setupStudentsTable() {


        TableColumn<Student,String> id =
                new TableColumn<>("رقم الطالب");


        id.setCellValueFactory(
                new PropertyValueFactory<>("studentId")
        );



        TableColumn<Student,String> name =
                new TableColumn<>("اسم الطالب");


        name.setCellValueFactory(
                new PropertyValueFactory<>("name")
        );



        TableColumn<Student,String> className =
                new TableColumn<>("السنة");


        className.setCellValueFactory(
                new PropertyValueFactory<>("className")
        );



        studentsTable.getColumns()
                .addAll(
                        id,
                        name,
                        className
                );



        studentsTable.setColumnResizePolicy(
                TableView.CONSTRAINED_RESIZE_POLICY
        );

    }







    private static void setupGradesTable() {


        TableColumn<Grade,String> course =
                new TableColumn<>("المادة");


        course.setCellValueFactory(
                new PropertyValueFactory<>("courseName")
        );



        TableColumn<Grade,Double> score =
                new TableColumn<>("الدرجة");


        score.setCellValueFactory(
                new PropertyValueFactory<>("score")
        );



        TableColumn<Grade,String> status =
                new TableColumn<>("الحالة");


        status.setCellValueFactory(
                new PropertyValueFactory<>("status")
        );



        gradesTable.getColumns()
                .addAll(
                        course,
                        score,
                        status
                );



        gradesTable.setColumnResizePolicy(
                TableView.CONSTRAINED_RESIZE_POLICY
        );


    }

}