package Gravityhook;

import Gravityhook.Abstract.MovableObject;

public class Physics {

    public final double gravity = 0.000226;

    public void apply(MovableObject mo) {
        mo.yAcc += gravity * mo.getMass();
    }
}
