package Gravityhook.Abstract;

import Gravityhook.Interfaces.Movable;

public abstract class MovableObject extends GameObject implements Movable {
    public double xAcc = 0;
    public double yAcc = 0;

    protected MovableObject() {
        super();
    }

    public MovableObject(double x, double y) {
        super(x, y);
    }

    @Override
    public void init(double x, double y) {
        super.init(x, y);
    }
}
