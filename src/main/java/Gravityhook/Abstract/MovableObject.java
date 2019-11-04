package Gravityhook.Abstract;

import Gravityhook.Interfaces.Movable;

public abstract class MovableObject extends GameObject implements Movable {
    public double xAcc = 0;
    public double yAcc = 0;

    public MovableObject(int x, int y) {
        super(x, y);
    }

    public void move(double milis) {
        x += xAcc * milis;
        y += yAcc * milis;
    }
}
