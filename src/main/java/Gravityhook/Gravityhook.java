package Gravityhook;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class Gravityhook {

    private Canvas canvas;

    public Gravityhook(Canvas canvas) {
        this.canvas = canvas;
    }

    public void game(GameController game) {
        new Thread(this::redraw).start();
    }

    private void redraw() {

    }

}
