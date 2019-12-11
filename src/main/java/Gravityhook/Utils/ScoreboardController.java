package Gravityhook.Utils;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import javax.xml.parsers.ParserConfigurationException;
import java.io.FileNotFoundException;

public class ScoreboardController {

    @FXML
    BorderPane rootPane;

    @FXML
    TableView table;

    @FXML
    private TableColumn<Scoreboard.ScoreboardRow, String> nickname;
    @FXML
    private TableColumn<Scoreboard.ScoreboardRow, String> date;
    @FXML
    private TableColumn<Scoreboard.ScoreboardRow, String> score;

    public void initialize() {
        Stage s = new Stage();
        s.setTitle("Scoreboards");
        s.setScene(new Scene(rootPane, 450, 450));
        nickname.setCellValueFactory(new PropertyValueFactory<Scoreboard.ScoreboardRow, String>("nickname"));
        date.setCellValueFactory(new PropertyValueFactory<Scoreboard.ScoreboardRow, String>("date"));
        score.setCellValueFactory(new PropertyValueFactory<Scoreboard.ScoreboardRow, String>("score"));

        try {
            table.getItems().setAll(new Scoreboard().readScores());
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        score.setSortType(TableColumn.SortType.DESCENDING);
        table.getSortOrder().setAll(score);
        s.show();
    }
}
