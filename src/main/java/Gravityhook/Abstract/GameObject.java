package Gravityhook.Abstract;

import Gravityhook.Interfaces.Drawable;
import Gravityhook.Interfaces.IHitbox;

public abstract class GameObject implements Drawable, IHitbox {
    public int x;
    public int y;
    public final double mass = 5;

    public GameObject(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
