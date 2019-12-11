package Gravityhook.GameObjects;

import Gravityhook.Abstract.GameObject;
import Gravityhook.Abstract.MovableObject;
import Gravityhook.Interfaces.Movable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Obstacle extends MovableObject {

    public Obstacle(double x, double y) {
        super(x, y);
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(Color.DARKORANGE);
        gc.fillRect(x, y, 50, 10);
    }

    @Override
    public boolean intersects(GameObject go) {
        if (Math.abs(this.x - go.x) <= getWidth() && Math.abs(this.y - go.y) <= getHeight())
            return true;
        return false;
    }

    @Override
    public double getWidth() {
        return 50;
    }

    @Override
    public double getHeight() {
        return 10;
    }

    public static int getWidthStatic() {
        return 10;
    }

    @Override
    public Movable move(double milis) {
        x += xAcc * milis;
        y += yAcc * milis;
        return this;
    }

    @Override
    public Movable fixCoords(double maxWidth, double maxHeight) {
        return this;
    }

    @Override
    public double getMass() {
        return 0;
    }
}
