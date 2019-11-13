package Gravityhook.Abstract;

import Gravityhook.Interfaces.Drawable;
import Gravityhook.Interfaces.IHitbox;

public abstract class GameObject implements Drawable, IHitbox {
    public double x;
    public double y;

    public GameObject(double x, double y) {
        this.x = x;
        this.y = y;
    }
}
