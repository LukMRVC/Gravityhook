package Gravityhook;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class GameController {

    @FXML
    BorderPane root;

    @FXML
    Canvas canvas;

    @FXML
    VBox menuBox;

    public GameController start() {
        FXMLLoader loader = new FXMLLoader(GameController.class.getResource("main.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        GameController c = loader.getController();
        c.init();
        return c;
    }

    public Scene createScene(int x, int y) {
        return new Scene(root, x, y);
    }

    private void init() {

    }

    public void startGame() {
        menuBox.setVisible(false);
        new Gravityhook(canvas).game(this);
    }

}
