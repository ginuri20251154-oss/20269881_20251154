package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.application.Application.launch;

public class InventoryApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader loader = new FXMLLoader(
                InventoryApplication.class.getResource(
                        "/org/example/Main.fxml"
                )
        );

        Scene scene = new Scene(
                loader.load(),
                1000,
                650
        );

        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
