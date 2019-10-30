package Gravityhook;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public final int HEIGHT = 700;
    public final int WIDTH = 400;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        GameController controller = new GameController();
        controller = controller.start();
        Scene s = controller.createScene(HEIGHT, WIDTH);
        primaryStage.setTitle("Gravityhook");
        primaryStage.setScene(s);
        primaryStage.show();
        primaryStage.setOnCloseRequest(x -> System.exit(0));
    }
}
