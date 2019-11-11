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

    public void setAccOnForce(double force, double angle) {
        this.xAcc += Math.cos(angle) * force * mass;
        this.yAcc += Math.sin(angle) * force * mass;
    }

    private void fixCoords() {
        if (x >= 360 || x <= 0) {
            xAcc = 0;
            x = 380 - 20;
        }
        if (y >= 630 || y <= 0) {
            yAcc = 0;
            y = 630;
        }
    }

    public void move(double milis) {
        x += xAcc * milis;
        y += yAcc * milis;
        fixCoords();
    }

}
