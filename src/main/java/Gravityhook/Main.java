package Gravityhook;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Label l = new Label("Hello, world!");
        Scene s = new Scene(l);
        primaryStage.setScene(s);
        primaryStage.show();

    }
}
