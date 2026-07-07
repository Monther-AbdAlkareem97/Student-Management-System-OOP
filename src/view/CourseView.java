package view;

import controller.ClassController;
import controller.CourseController;
import controller.TeacherController;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.Course;
import model.SchoolClass;
import model.Teacher;

import java.util.List;

public class CourseView {

    private static TableView<Course> table = new TableView<>();

    private static TextField courseNameField = new TextField();

    private static TextField creditsField = new TextField();

    private static ComboBox<SchoolClass> classCombo = new ComboBox<>();

    private static ComboBox<Teacher> teacherCombo = new ComboBox<>();



    public static void show(Stage stage) {


        setupTable();

        loadCourses();

        loadClasses();

        loadTeachers();



        // ===== Form =====

        GridPane form = new GridPane();

        form.setHgap(10);

        form.setVgap(10);

        form.setPadding(new Insets(10));

        form.getStyleClass()
                .add("form-panel");



        courseNameField.getStyleClass()
                .add("text-input");

        creditsField.getStyleClass()
                .add("text-input");

        classCombo.getStyleClass()
                .add("combo-box");

        teacherCombo.getStyleClass()
                .add("combo-box");



        form.addRow(
                0,
                new Label("اسم المادة:"),
                courseNameField
        );


        form.addRow(
                1,
                new Label("الصف:"),
                classCombo
        );


        form.addRow(
                2,
                new Label("الأستاذ:"),
                teacherCombo
        );


        form.addRow(
                3,
                new Label("عدد الساعات:"),
                creditsField
        );




        // ===== Buttons =====

        Button addBtn =
                new Button("➕ إضافة");


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



        addBtn.setOnAction(e -> addCourse());

        updateBtn.setOnAction(e -> updateCourse());

        deleteBtn.setOnAction(e -> deleteCourse());

        clearBtn.setOnAction(e -> clearFields());



        HBox buttons =
                new HBox(
                        10,
                        addBtn,
                        updateBtn,
                        deleteBtn,
                        clearBtn
                );


        buttons.setPadding(
                new Insets(10,0,0,0)
        );



        table.setOnMouseClicked(
                e -> fillFormFromSelection()
        );



        // ===== Header =====

        Label eyebrow =
                new Label("إدارة");


        Label title =
                new Label("المواد الدراسية");



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



        // ===== Table Panel =====

        VBox tablePanel =
                new VBox(table);


        tablePanel.getStyleClass()
                .add("panel");



        // ===== Main =====

        VBox mainContent =
                new VBox(
                        20,
                        header,
                        tablePanel,
                        form,
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
                        "courses"
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
                    CourseView.class
                    .getResource("/styles.css")
                    .toExternalForm()
                );


        stage.setTitle("إدارة المواد");

        stage.setScene(scene);

        stage.setMaximized(true);
        stage.setWidth(1200);
        stage.setHeight(700);
        stage.show();

    }
    
    @SuppressWarnings("unchecked")
    private static void setupTable() {

        table.getColumns().clear();

        table.getStyleClass()
                .add("table-view");



        TableColumn<Course, String> nameCol =
                new TableColumn<>("اسم المادة");


        nameCol.setCellValueFactory(
                new PropertyValueFactory<>("courseName")
        );



        TableColumn<Course, String> classCol =
                new TableColumn<>("الصف");


        classCol.setCellValueFactory(
                new PropertyValueFactory<>("className")
        );



        TableColumn<Course, String> teacherCol =
                new TableColumn<>("الأستاذ");


        teacherCol.setCellValueFactory(
                new PropertyValueFactory<>("teacherName")
        );



        TableColumn<Course, Integer> creditsCol =
                new TableColumn<>("الساعات");


        creditsCol.setCellValueFactory(
                new PropertyValueFactory<>("credits")
        );



        table.getColumns()
                .addAll(
                        nameCol,
                        classCol,
                        teacherCol,
                        creditsCol
                );



        table.setColumnResizePolicy(
                TableView.CONSTRAINED_RESIZE_POLICY
        );

    }





    private static void loadCourses() {

        List<Course> courses =
                CourseController.getAllCourses();


        table.setItems(
                FXCollections.observableArrayList(
                        courses
                )
        );

    }





    private static void loadClasses() {

        List<SchoolClass> classes =
                ClassController.getAllClasses();


        classCombo.setItems(
                FXCollections.observableArrayList(
                        classes
                )
        );

    }





    private static void loadTeachers() {

        List<Teacher> teachers =
                TeacherController.getAllTeachers();


        teacherCombo.setItems(
                FXCollections.observableArrayList(
                        teachers
                )
        );

    }





    private static void addCourse() {


        SchoolClass selectedClass =
                classCombo.getValue();


        Teacher selectedTeacher =
                teacherCombo.getValue();



        if(selectedClass == null ||
                selectedTeacher == null) {


            showError(
                    "اختر الصف والأستاذ أولاً"
            );


            return;

        }



        try {


            Course c =
                    new Course(
                            0,
                            courseNameField.getText(),

                            selectedClass.getId(),

                            selectedClass.getClassName(),

                            selectedTeacher.getId(),

                            selectedTeacher.getName(),

                            Integer.parseInt(
                                    creditsField.getText()
                            )
                    );



            CourseController.addCourse(c);



            loadCourses();


            clearFields();



        } catch(NumberFormatException e) {


            showError(
                    "تأكد من إدخال عدد الساعات كرقم صحيح"
            );

        }

    }





    private static void updateCourse() {


        Course selected =
                table.getSelectionModel()
                .getSelectedItem();



        if(selected == null) {


            showError(
                    "اختر مادة للتعديل"
            );


            return;

        }




        SchoolClass selectedClass =
                classCombo.getValue();


        Teacher selectedTeacher =
                teacherCombo.getValue();



        if(selectedClass == null ||
                selectedTeacher == null) {


            showError(
                    "اختر الصف والأستاذ أولاً"
            );


            return;

        }





        try {


            Course c =
                    new Course(
                            selected.getCourseId(),

                            courseNameField.getText(),

                            selectedClass.getId(),

                            selectedClass.getClassName(),

                            selectedTeacher.getId(),

                            selectedTeacher.getName(),

                            Integer.parseInt(
                                    creditsField.getText()
                            )
                    );



            CourseController.updateCourse(c);



            loadCourses();


            clearFields();



        } catch(NumberFormatException e) {


            showError(
                    "تأكد من إدخال عدد الساعات كرقم صحيح"
            );

        }

    }





    private static void deleteCourse() {


        Course selected =
                table.getSelectionModel()
                .getSelectedItem();



        if(selected == null) {


            showError(
                    "اختر مادة للحذف"
            );


            return;

        }



        CourseController.deleteCourse(
                selected.getCourseId()
        );



        loadCourses();


        clearFields();

    }





    private static void fillFormFromSelection() {


        Course selected =
                table.getSelectionModel()
                .getSelectedItem();



        if(selected != null) {


            courseNameField.setText(
                    selected.getCourseName()
            );


            creditsField.setText(
                    String.valueOf(
                            selected.getCredits()
                    )
            );



            for(SchoolClass sc : classCombo.getItems()) {


                if(sc.getId()
                        == selected.getClassId()) {


                    classCombo.setValue(sc);

                    break;

                }

            }



            for(Teacher t : teacherCombo.getItems()) {


                if(t.getId()
                        == selected.getTeacherId()) {


                    teacherCombo.setValue(t);

                    break;

                }

            }

        }

    }





    private static void clearFields() {


        courseNameField.clear();

        creditsField.clear();

        classCombo.setValue(null);

        teacherCombo.setValue(null);

    }





    private static void showError(String message) {


        Alert alert =
                new Alert(
                        Alert.AlertType.ERROR
                );


        alert.setTitle("خطأ");

        alert.setHeaderText(null);

        alert.setContentText(message);


        alert.showAndWait();

    }

}