package Gravityhook;

import Gravityhook.Utils.Scoreboard;
import Gravityhook.Utils.ScoreboardController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

public class GameController {

    @FXML
    BorderPane root;

    @FXML
    public Canvas canvas;

    @FXML
    VBox menuBox;

    Stage stage;

    private Gravityhook game;

    @FXML
    private TextField playerName;

    private String playerStr;

    private Scene scene;

    private Scoreboard scoreboard;

    private MenuControl menuControl;

    public GameController() {
        this.scoreboard = new Scoreboard();
        this.menuControl = new MenuControl(this);
    }

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

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Stage getStage() {
        return this.stage;
    }

    public void startGame() {

        this.playerStr = playerName.getText();
        if (playerStr.length() < 3) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Player name must be at least 3 characters long.", ButtonType.OK);
            a.setTitle("Invalid player name");
            a.show();
            return;
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
        System.out.println(getClass().getResource("scoreboard.fxml"));
        System.out.println(getClass().getClassLoader().getResource("scoreboard.fxml"));
        System.out.println(GameController.class.getResource("scoreboard.fxml"));

        FXMLLoader loader = new FXMLLoader(getClass().getResource("scoreboard.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        ScoreboardController sc = loader.getController();
    }

    public void endGame() {
        scene.setCursor(Cursor.DEFAULT);
        Button button = new Button("Continue");
        root.getChildren().remove(canvas);
        root.getChildren().add(menuBox);
        menuBox.getChildren().add(new Label(this.playerStr + ", your score is: " + game.score));
        try {
            Scoreboard.ScoreboardRow row = new Scoreboard.ScoreboardRow(game.score, this.playerStr);
            scoreboard.writeScore(row);
        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
        game.score = 0;
        game = null;
    }

    public void resize() {

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

    public void aboutItemAction(ActionEvent actionEvent) {
        menuControl.showAbout();
    }

    public void delegateSizeAction1(ActionEvent actionEvent) {
        menuControl.size1();
    }

    public void delegateSizeAction2(ActionEvent actionEvent) {
        menuControl.size2();
    }
}
