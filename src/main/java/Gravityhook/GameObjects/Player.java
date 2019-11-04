package Gravityhook.GameObjects;

import Gravityhook.Abstract.GameObject;
import Gravityhook.Abstract.MovableObject;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class Player extends MovableObject {

    public final double mass = 150;


    public Player(int x, int y) {
        super(x, y);
    }

    @Override
    public void draw(GraphicsContext gc) {
        Paint orig = gc.getFill();
        gc.setFill(Color.CHOCOLATE);
        gc.fillRect(x, y, 20, 30);
        gc.setFill(orig);
    }

    @Override
    public boolean intersects(GameObject go) {
        return false;
    }

}
