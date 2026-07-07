package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class LoginView {

    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin123";

    public static void show(Stage stage) {

        // ===== الجانب الأيسر (Navy) =====
        VBox leftPanel = new VBox(12);
        leftPanel.getStyleClass().add("login-left-panel");
        leftPanel.setAlignment(Pos.CENTER);
        leftPanel.setPrefWidth(280);

        Label titleLabel    = new Label("نظام إدارة الطلاب");
        Label subtitleLabel = new Label("المنصة الرسمية لإدارة شؤون\nالطلاب والأساتذة والمواد الدراسية");

        titleLabel.getStyleClass().add("login-title");
        subtitleLabel.getStyleClass().add("login-subtitle");
        subtitleLabel.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);

        leftPanel.getChildren().addAll(titleLabel, subtitleLabel);

        // ===== الجانب الأيمن (Form) =====
        VBox rightPanel = new VBox(14);
        rightPanel.getStyleClass().add("login-right-panel");
        rightPanel.setAlignment(Pos.CENTER_RIGHT);
        rightPanel.setPrefWidth(380);

        Label eyebrow  = new Label("تسجيل الدخول");
        Label heading  = new Label("مرحباً بك مجدداً");
        eyebrow.getStyleClass().add("login-eyebrow");
        heading.getStyleClass().add("login-heading");

        Label userLabel = new Label("اسم المستخدم");
        userLabel.getStyleClass().add("field-label");
        TextField userField = new TextField();
        userField.setPromptText("admin");
        userField.getStyleClass().add("text-input");

        Label passLabel = new Label("كلمة المرور");
        passLabel.getStyleClass().add("field-label");
        PasswordField passField = new PasswordField();
        passField.setPromptText("••••••••");
        passField.getStyleClass().add("text-input");

        Button loginBtn = new Button("تسجيل الدخول");
        loginBtn.getStyleClass().add("login-btn");

        Label hintLabel = new Label("هذا النظام مخصص لإدارة المدرسة فقط");
        hintLabel.setStyle("-fx-font-size: 11px; -fx-text-fill: #a9ad9e;");

        loginBtn.setOnAction(e -> {
            if (userField.getText().equals(ADMIN_USERNAME) &&
                passField.getText().equals(ADMIN_PASSWORD)) {
                DashboardView.show(stage);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("خطأ");
                alert.setHeaderText(null);
                alert.setContentText("اسم المستخدم أو كلمة المرور غير صحيحة");
                alert.showAndWait();
            }
        });

        rightPanel.getChildren().addAll(
            eyebrow, heading,
            userLabel, userField,
            passLabel, passField,
            loginBtn, hintLabel
        );

        // ===== تجميع الشاشة =====
     // ===== تجميع الشاشة =====
        HBox root = new HBox(leftPanel, rightPanel);
        root.getStyleClass().add("login-root");
        HBox.setHgrow(rightPanel, Priority.ALWAYS);  // ⬅️ خلي الجانب الأيمن يتمدد

        Scene scene = new Scene(root, 660, 420);
        scene.getStylesheets().add(
            LoginView.class.getResource("/styles.css").toExternalForm()
        );

        stage.setTitle("تسجيل الدخول - نظام إدارة الطلاب");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.setWidth(1200);
        stage.setHeight(700);
        stage.show();
    }
}