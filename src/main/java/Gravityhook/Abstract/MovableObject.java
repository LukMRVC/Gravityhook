package Gravityhook.Abstract;

import Gravityhook.Interfaces.Movable;

public abstract class MovableObject extends GameObject implements Movable {
    public double xAcc = 0;
    public double yAcc = 0;
    public final double mass = 5;


    public MovableObject(int x, int y) {
        super(x, y);
    }


}
