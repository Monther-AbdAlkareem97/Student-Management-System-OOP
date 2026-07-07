package view;

import controller.ClassController;
import controller.CourseController;
import controller.GradeController;
import controller.StudentController;
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

public class GradeView {

    private static TableView<Grade> table = new TableView<>();

    private static ComboBox<SchoolClass> classCombo =
            new ComboBox<>();

    private static ComboBox<Student> studentCombo =
            new ComboBox<>();

    private static ComboBox<Course> courseCombo =
            new ComboBox<>();

    private static TextField scoreField =
            new TextField();

    private static Label gpaLabel =
            new Label("المعدل التراكمي (GPA): -");


    public static void show(Stage stage) {

        setupTable();

        loadClasses();


        // اختيار الصف
        classCombo.setOnAction(e -> {

            loadStudentsByClass();

            courseCombo.getItems().clear();

            table.getItems().clear();

            gpaLabel.setText(
                    "المعدل التراكمي (GPA): -"
            );
        });



        // اختيار الطالب
        studentCombo.setOnAction(e -> {

            loadCoursesForSelectedStudent();

            loadGradesForSelectedStudent();

        });



        GridPane form = new GridPane();

        form.setHgap(10);
        form.setVgap(10);
        form.setPadding(new Insets(10));

        form.getStyleClass()
                .add("form-panel");


        classCombo.getStyleClass()
                .add("combo-box");

        studentCombo.getStyleClass()
                .add("combo-box");

        courseCombo.getStyleClass()
                .add("combo-box");

        scoreField.getStyleClass()
                .add("text-input");



        form.addRow(0,
                new Label("الصف:"),
                classCombo);


        form.addRow(1,
                new Label("الطالب:"),
                studentCombo);


        form.addRow(2,
                new Label("المادة:"),
                courseCombo);


        form.addRow(3,
                new Label("الدرجة:"),
                scoreField);



        Button addBtn =
                new Button("➕ إضافة درجة");

        Button updateBtn =
                new Button("✏️ تعديل");

        Button deleteBtn =
                new Button("🗑️ حذف");

        Button clearBtn =
                new Button("🔄 تفريغ");



        addBtn.getStyleClass()
                .add("btn-primary");

        updateBtn.getStyleClass()
                .add("btn-primary");

        deleteBtn.getStyleClass()
                .add("btn-danger");

        clearBtn.getStyleClass()
                .add("btn-secondary");



        addBtn.setOnAction(e -> addGrade());

        updateBtn.setOnAction(e -> updateGrade());

        deleteBtn.setOnAction(e -> deleteGrade());

        clearBtn.setOnAction(e -> clearFields());



        HBox buttons =
                new HBox(
                        10,
                        addBtn,
                        updateBtn,
                        deleteBtn,
                        clearBtn
                );


        table.setOnMouseClicked(
                e -> fillScoreFromSelection()
        );



        Label eyebrow =
                new Label("إدارة");


        Label title =
                new Label("الدرجات");


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



        VBox tablePanel =
                new VBox(table);

        tablePanel.getStyleClass()
                .add("panel");



        gpaLabel.getStyleClass()
                .add("panel-title");



        VBox mainContent =
                new VBox(
                        20,
                        header,
                        tablePanel,
                        form,
                        gpaLabel,
                        buttons
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
                        "grades"
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
                        GradeView.class
                        .getResource("/styles.css")
                        .toExternalForm()
                );


        stage.setTitle(
                "إدارة الدرجات"
        );

        stage.setScene(scene);

        stage.setMaximized(true);

        stage.show();

    }



    @SuppressWarnings("unchecked")
    private static void setupTable(){

        table.getColumns().clear();


        table.getStyleClass()
                .add("table-view");



        TableColumn<Grade,String> courseCol =
                new TableColumn<>("المادة");


        courseCol.setCellValueFactory(
                new PropertyValueFactory<>("courseName")
        );


        TableColumn<Grade,Double> scoreCol =
                new TableColumn<>("الدرجة");


        scoreCol.setCellValueFactory(
                new PropertyValueFactory<>("score")
        );



        TableColumn<Grade,String> statusCol =
                new TableColumn<>("الحالة");


        statusCol.setCellValueFactory(
                new PropertyValueFactory<>("status")
        );


        table.getColumns()
                .addAll(
                        courseCol,
                        scoreCol,
                        statusCol
                );


        table.setColumnResizePolicy(
                TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN
        );

    }



    private static void loadClasses(){

        classCombo.setItems(
                FXCollections.observableArrayList(
                        ClassController.getAllClasses()
                )
        );
    }



    private static void loadStudentsByClass(){

        SchoolClass sc =
                classCombo.getValue();


        if(sc == null)
            return;


        List<Student> students =
                StudentController.getStudentsByClassId(
                        sc.getId()
                );


        studentCombo.setItems(
                FXCollections.observableArrayList(
                        students
                )
        );

    }



    private static void loadCoursesForSelectedStudent(){

        Student student =
                studentCombo.getValue();


        if(student == null)
            return;


        List<Course> courses =
                CourseController.getCoursesByClassId(
                        student.getClassId()
                );


        courseCombo.setItems(
                FXCollections.observableArrayList(
                        courses
                )
        );

    }



    private static void loadGradesForSelectedStudent(){

        Student student =
                studentCombo.getValue();


        if(student == null)
            return;


        table.setItems(
                FXCollections.observableArrayList(
                        GradeController.getGradesByStudentId(
                                student.getId()
                        )
                )
        );


        double gpa =
                GradeController.calculateGPA(
                        student.getId()
                );


        gpaLabel.setText(
                "المعدل التراكمي (GPA): "
                + String.format("%.2f", gpa)
        );

    
    }
    private static void addGrade() {

        Student student = studentCombo.getValue();
        Course course = courseCombo.getValue();


        if (student == null || course == null) {

            showError("اختر الطالب والمادة أولاً");
            return;
        }


        try {

            double score =
                    Double.parseDouble(
                            scoreField.getText()
                    );


            GradeController.addGrade(
                    student.getId(),
                    course.getCourseId(),
                    score
            );


            loadGradesForSelectedStudent();

            scoreField.clear();


        } catch (NumberFormatException e) {

            showError(
                    "أدخل الدرجة كرقم صحيح"
            );
        }

    }




    private static void updateGrade() {


        Grade selected =
                table.getSelectionModel()
                .getSelectedItem();



        if(selected == null){

            showError(
                    "اختر درجة من الجدول للتعديل"
            );

            return;
        }



        try {

            double score =
                    Double.parseDouble(
                            scoreField.getText()
                    );



            GradeController.updateGrade(
                    selected.getGradeId(),
                    score
            );



            loadGradesForSelectedStudent();

            scoreField.clear();



        } catch(NumberFormatException e){

            showError(
                    "أدخل الدرجة كرقم صحيح"
            );
        }

    }





    private static void deleteGrade(){


        Grade selected =
                table.getSelectionModel()
                .getSelectedItem();



        if(selected == null){

            showError(
                    "اختر درجة للحذف"
            );

            return;
        }



        GradeController.deleteGrade(
                selected.getGradeId()
        );



        loadGradesForSelectedStudent();

    }





    private static void fillScoreFromSelection(){


        Grade selected =
                table.getSelectionModel()
                .getSelectedItem();



        if(selected != null){


            scoreField.setText(
                    String.valueOf(
                            selected.getScore()
                    )
            );



            for(Course c : courseCombo.getItems()){


                if(c.getCourseId() ==
                        selected.getCourseId()){


                    courseCombo.setValue(c);

                    break;
                }
            }

        }

    }





    private static void clearFields(){


        classCombo.setValue(null);


        studentCombo.getItems().clear();

        studentCombo.setValue(null);


        courseCombo.getItems().clear();

        courseCombo.setValue(null);


        scoreField.clear();


        table.getItems().clear();


        gpaLabel.setText(
                "المعدل التراكمي (GPA): -"
        );

    }





    private static void showError(String message){


        Alert alert =
                new Alert(
                        Alert.AlertType.ERROR
                );


        alert.setTitle(
                "خطأ"
        );


        alert.setHeaderText(null);


        alert.setContentText(
                message
        );


        alert.showAndWait();

    }

}