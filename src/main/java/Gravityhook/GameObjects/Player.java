package Gravityhook.GameObjects;

import Gravityhook.Abstract.GameObject;
import Gravityhook.Abstract.MovableObject;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class Player extends MovableObject {

    public final double mass = 40;

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

    @Override
    public double getWidth() {
        return 20;
    }

    @Override
    public double getHeight() {
        return 30;
    }

    public void setAccOnForce(double force, double angle) {
        this.xAcc += Math.cos(angle) * force * mass;
        this.yAcc += Math.sin(angle) * force * mass;
    }

    public MovableObject fixCoords(double maxWidth, double maxHeight) {
        if (x + getWidth() > maxWidth || x  < 0) {
            xAcc /= -2;
        }
        if (y + getHeight() >= maxHeight)
            yAcc *= -1;
        return this;
    }

    public MovableObject move(double milis) {
        x += xAcc * milis;
        y += yAcc * milis;
        return this;
    }

    public double getMass() {
        return this.mass;
    }

}
