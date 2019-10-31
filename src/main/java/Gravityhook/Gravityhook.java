package Gravityhook;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.Random;

public class Gravityhook {

    private Canvas canvas;

    public Gravityhook(Canvas canvas) {
        this.canvas = canvas;
    }

    public void game(GameController game) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.GREEN);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        new Thread(this::redraw).start();
    }

    private void redraw() {
        while(true) {
            GraphicsContext gc = canvas.getGraphicsContext2D();
            gc.setFill(Color.rgb(new Random().nextInt(255), new Random().nextInt(255), new Random().nextInt(255)));
            gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }

    }

}
