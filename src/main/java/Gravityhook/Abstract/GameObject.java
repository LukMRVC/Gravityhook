package Gravityhook.Abstract;

import Gravityhook.Interfaces.Drawable;
import Gravityhook.Interfaces.IHitbox;
import Gravityhook.Interfaces.Initializable;

public abstract class GameObject implements Drawable, IHitbox, Initializable {
    public double x;
    public double y;

    protected GameObject() {
        x = 0;
        y = 0;
    }

    public GameObject(double x, double y) {
        init(x, y);
    }

    public void init(double x, double y) {
        this.x = x;
        this.y = y;
    }
}
