package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginView {

    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin123";

    public static void show(Stage stage) {
        Label titleLabel = new Label("نظام إدارة الطلاب");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Label userLabel = new Label("اسم المستخدم:");
        TextField userField = new TextField();
        userField.setPromptText("admin");

        Label passLabel = new Label("كلمة المرور:");
        PasswordField passField = new PasswordField();
        passField.setPromptText("password");

        Button loginButton = new Button("تسجيل الدخول");
        loginButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-size: 14px;");

        loginButton.setOnAction(e -> {
            String username = userField.getText();
            String password = passField.getText();

            if (username.equals(ADMIN_USERNAME) && password.equals(ADMIN_PASSWORD)) {
                // نجاح تسجيل الدخول → نفتح الداشبورد
                DashboardView.show(stage);
            } else {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("خطأ");
                alert.setHeaderText(null);
                alert.setContentText("اسم المستخدم أو كلمة المرور غير صحيحة");
                alert.showAndWait();
            }
        });

        VBox layout = new VBox(15);
        layout.setPadding(new Insets(40));
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(titleLabel, userLabel, userField, passLabel, passField, loginButton);

        Scene scene = new Scene(layout, 400, 350);
        stage.setTitle("تسجيل الدخول");
        stage.setScene(scene);
        stage.show();
    }
}