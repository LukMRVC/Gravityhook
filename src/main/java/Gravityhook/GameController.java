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
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.lang.management.PlatformLoggingMXBean;
import java.util.Optional;

public class GameController {

    @FXML
    BorderPane root;

    @FXML
    Canvas canvas;

    @FXML
    VBox menuBox;

    private Gravityhook game;

    private String playerName;

    private Scene scene;

    public GameController start() {
        game = null;
        playerName = "Player";
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
        Dialog dialog = new TextInputDialog(this.playerName);
        dialog.setTitle("Set player name");
        dialog.setHeaderText("Please enter your nickname.");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            this.playerName = result.get();
        }
        menuBox.getChildren().removeIf(Node -> Node instanceof Label);
        root.getChildren().remove(menuBox);
        root.getChildren().add(canvas);
        game = new Gravityhook(canvas);
        game.setScene(scene);
        game.game(this);
    }

    public void scoreboard()
    {
        return;
    }

    public void endGame() {
        scene.setCursor(Cursor.DEFAULT);
        Button button = new Button("Continue");
        root.getChildren().remove(canvas);
        root.getChildren().add(menuBox);
        menuBox.getChildren().add(new Label(this.playerName + ", your score is: " + game.score));
        game.score = 0;
        game = null;
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
