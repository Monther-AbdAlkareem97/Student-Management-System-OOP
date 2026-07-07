package view;

import controller.ClassController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.SchoolClass;

import java.util.List;

public class ClassView {

    private static TableView<SchoolClass> table = new TableView<>();

    private static ComboBox<String> levelCombo = new ComboBox<>();
    private static ComboBox<String> yearCombo = new ComboBox<>();


    public static void show(Stage stage) {

        setupTable();
        loadClasses();


        // ===== Form =====

        GridPane form = new GridPane();

        form.setHgap(10);
        form.setVgap(10);
        form.setPadding(new Insets(10));

        form.getStyleClass().add("form-panel");


        levelCombo.getStyleClass().add("combo-box");
        yearCombo.getStyleClass().add("combo-box");


        levelCombo.setItems(
                FXCollections.observableArrayList(
                        "ابتدائي",
                        "إعدادي",
                        "ثانوي"
                )
        );


        levelCombo.setOnAction(e -> updateYears());


        form.addRow(
                0,
                new Label("المرحلة:"),
                levelCombo
        );


        form.addRow(
                1,
                new Label("السنة:"),
                yearCombo
        );



        // ===== Buttons =====

        Button addBtn = new Button("➕ إضافة");
        Button updateBtn = new Button("✏️ تعديل");
        Button deleteBtn = new Button("🗑️ حذف");
        Button clearBtn = new Button("🔄 تفريغ");


        addBtn.getStyleClass().add("btn-primary");
        updateBtn.getStyleClass().add("btn-primary");
        deleteBtn.getStyleClass().add("btn-danger");
        clearBtn.getStyleClass().add("btn-secondary");


        addBtn.setOnAction(e -> addClass());
        updateBtn.setOnAction(e -> updateClass());
        deleteBtn.setOnAction(e -> deleteClass());
        clearBtn.setOnAction(e -> clearFields());


        HBox buttons = new HBox(
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

        Label eyebrow = new Label("إدارة");

        Label title = new Label("الصفوف الدراسية");


        eyebrow.getStyleClass()
                .add("page-eyebrow");

        title.getStyleClass()
                .add("page-title");


        VBox header = new VBox(
                4,
                eyebrow,
                title
        );



        // ===== Table Panel =====

        VBox tablePanel = new VBox(table);

        tablePanel.getStyleClass()
                .add("panel");



        // ===== Main Content =====

        VBox mainContent = new VBox(
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



        // ===== Sidebar =====

        VBox sidebar =
                DashboardView.createSidebar(
                        stage,
                        "classes"
                );


        HBox root =
                new HBox(
                        mainContent,
                        sidebar
                );



        Scene scene =
                new Scene(root);


        scene.getStylesheets().add(
                ClassView.class
                .getResource("/styles.css")
                .toExternalForm()
        );


        stage.setTitle("إدارة الصفوف");

        stage.setScene(scene);

        stage.setMaximized(true);
        stage.setWidth(1200);
        stage.setHeight(700);
        stage.show();
    }



    private static void updateYears() {

        yearCombo.getItems().clear();


        String level =
                levelCombo.getValue();


        if(level == null)
            return;



        switch(level) {

            case "ابتدائي":

                yearCombo.getItems().addAll(
                        "الأولى",
                        "الثانية",
                        "الثالثة",
                        "الرابعة",
                        "الخامسة",
                        "السادسة"
                );

                break;


            case "إعدادي":
            case "ثانوي":

                yearCombo.getItems().addAll(
                        "الأولى",
                        "الثانية",
                        "الثالثة"
                );

                break;
        }

    }
    
    @SuppressWarnings("unchecked")
    private static void setupTable() {

        table.getColumns().clear();

        table.getStyleClass()
                .add("table-view");


        TableColumn<SchoolClass, Integer> idCol =
                new TableColumn<>("رقم الصف");

        idCol.setCellValueFactory(
                new PropertyValueFactory<>("id")
        );



        TableColumn<SchoolClass, String> nameCol =
                new TableColumn<>("اسم الصف");

        nameCol.setCellValueFactory(
                new PropertyValueFactory<>("className")
        );



        TableColumn<SchoolClass, String> levelCol =
                new TableColumn<>("المرحلة");

        levelCol.setCellValueFactory(
                new PropertyValueFactory<>("level")
        );



        table.getColumns().addAll(
                idCol,
                nameCol,
                levelCol
        );


        table.setColumnResizePolicy(
                TableView.CONSTRAINED_RESIZE_POLICY
        );

    }



    private static void loadClasses() {

        List<SchoolClass> classes =
                ClassController.getAllClasses();


        ObservableList<SchoolClass> data =
                FXCollections.observableArrayList(classes);


        table.setItems(data);

    }





    private static void addClass() {


        if(levelCombo.getValue() == null ||
                yearCombo.getValue() == null) {

            showError("اختر المرحلة والسنة أولاً");
            return;
        }



        String className =
                yearCombo.getValue()
                + " "
                + levelCombo.getValue();



        SchoolClass sc =
                new SchoolClass(
                        0,
                        className,
                        levelCombo.getValue()
                );



        ClassController.addClass(sc);


        loadClasses();

        clearFields();

    }





    private static void updateClass() {


        SchoolClass selected =
                table.getSelectionModel()
                .getSelectedItem();



        if(selected == null) {

            showError("اختر صفاً للتعديل");
            return;
        }



        if(levelCombo.getValue() == null ||
                yearCombo.getValue() == null) {

            showError("اختر المرحلة والسنة أولاً");
            return;
        }




        String className =
                yearCombo.getValue()
                + " "
                + levelCombo.getValue();




        SchoolClass sc =
                new SchoolClass(
                        selected.getId(),
                        className,
                        levelCombo.getValue()
                );



        ClassController.updateClass(sc);


        loadClasses();

        clearFields();

    }





    private static void deleteClass() {


        SchoolClass selected =
                table.getSelectionModel()
                .getSelectedItem();



        if(selected == null) {

            showError("اختر صفاً للحذف");
            return;
        }




        ClassController.deleteClass(
                selected.getId()
        );



        loadClasses();

        clearFields();

    }





    private static void fillFormFromSelection() {


        SchoolClass selected =
                table.getSelectionModel()
                .getSelectedItem();



        if(selected != null) {


            levelCombo.setValue(
                    selected.getLevel()
            );



            updateYears();



            /*
             * استخراج السنة من اسم الصف
             * مثال:
             * الأولى ابتدائي
             */

            String className =
                    selected.getClassName();



            for(String year : yearCombo.getItems()) {

                if(className.startsWith(year)) {

                    yearCombo.setValue(year);

                    break;
                }
            }

        }

    }





    private static void clearFields() {

        levelCombo.setValue(null);

        yearCombo.getItems().clear();

        yearCombo.setValue(null);

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