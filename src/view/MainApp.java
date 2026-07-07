package view;

import database.DatabaseConnection;
import database.DatabaseSetup;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        // نشغل الاتصال وننشئ الجداول عند بداية التطبيق
        DatabaseConnection.getConnection();
        DatabaseSetup.createTables();
        
        LoginView.show(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}