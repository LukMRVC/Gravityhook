package Gravityhook.GameObjects;

import Gravityhook.Abstract.GameObject;
import Gravityhook.Abstract.MovableObject;
import Gravityhook.Interfaces.Clickable;
import Gravityhook.Interfaces.Connectable;
import Gravityhook.Interfaces.Movable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Graviton extends MovableObject implements Connectable, Clickable {

    private final double mass = 50;

    private boolean active = false;

    public Graviton(double x, double y) {
        super(x, y);
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(Color.TOMATO);
        gc.fillOval(x - getWidth() / 2, y - getHeight() / 2, 20, 20);
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
        return 20;
    }

    @Override
    public Movable move(double milis) {
        this.x += xAcc * milis;
        this.y += yAcc * milis;
        return this;
    }

    @Override
    public void setAccOnForce(double force, double angle) {
        xAcc = 0;
        yAcc = 0;

    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public Movable fixCoords(double maxWidth, double maxHeight) {
        return this;
    }

    @Override
    public double getMass() {
        return mass;
    }

    @Override
    public boolean isClicked(int x, int y) {
        if (Math.abs(this.x - x) < this.getWidth() && Math.abs(this.y - y) < this.getHeight()) {
            return true;
        }
        return false;
    }
}
