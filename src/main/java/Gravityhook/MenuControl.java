package Gravityhook;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class MenuControl {

    private GameController controller;

    public MenuControl(GameController controller) {
        this.controller = controller;
    }

    public void size1() {
        controller.getStage().setWidth(400);
        controller.canvas.setWidth(400);
    }

    public void size2() {
        controller.getStage().setWidth(800);
        controller.canvas.setWidth(800);
    }

    public void showAbout() {
        Alert a = new Alert(Alert.AlertType.INFORMATION, "Created by Lukas Moravec MOR0179.", ButtonType.OK);
        a.setTitle("Author");
        a.show();
    }
}
