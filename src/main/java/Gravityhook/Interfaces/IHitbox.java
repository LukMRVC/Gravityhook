package Gravityhook.Interfaces;

import Gravityhook.Abstract.GameObject;

public interface IHitbox {

    public boolean intersects(GameObject go);

    double getWidth();
    double getHeight();

}
