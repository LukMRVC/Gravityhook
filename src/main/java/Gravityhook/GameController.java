package Gravityhook;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.lang.management.PlatformLoggingMXBean;

public class GameController {

    @FXML
    BorderPane root;

    @FXML
    Canvas canvas;

    @FXML
    VBox menuBox;

    private Gravityhook game;

    private Scene scene;

    public GameController start() {
        game = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        GameController c = loader.getController();
        return c;
    }

    public Scene createScene(int x, int y) {
        return new Scene(root, x, y);
    }

    public void startGame() {
        root.getChildren().remove(menuBox);
        root.getChildren().add(canvas);
        game = new Gravityhook(canvas);
        game.setScene(scene);
        game.game(this);
    }

    public void endGame() {
        root.getChildren().remove(canvas);
        root.getChildren().add(menuBox);
        game = null;
        scene.setCursor(Cursor.DEFAULT);
    }

    public void mousePressed(MouseEvent mouseEvent) {
        if (game != null) {
            game.clicked(mouseEvent.getSceneX(), mouseEvent.getSceneY());
        }
    }

    public void mouseReleased(MouseEvent mouseEvent) {
        if (game != null) {
            game.released();
        }
    }

    public void mouseMove(MouseEvent mouseEvent) {
        if (game != null) {
            game.moved(mouseEvent.getSceneX(), mouseEvent.getSceneY());
        }
    }

    public void setScene(Scene s) {
        this.scene = s;
    }
}
