package Gravityhook;

import Gravityhook.Abstract.MovableObject;

public class Physics {

    public double gravity = 0.0099;

    public void apply(MovableObject mo) {
        mo.yAcc += gravity * mo.mass;
    }
}
