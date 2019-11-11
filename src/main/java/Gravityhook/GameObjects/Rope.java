package Gravityhook.GameObjects;

import Gravityhook.Abstract.GameObject;
import Gravityhook.Abstract.MovableObject;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class Rope extends GameObject {
    public static final double G = 1.6674;          //Gravitational constant, same for everything

    public MovableObject end1;

    public MovableObject end2;

    public double force;

    public Rope(int x, int y) {
        super(x, y);
    }

    public Rope(MovableObject go1, MovableObject go2) {
        super(go1.x, go1.y);
        end1 = go1;
        end2 = go2;

    }

    @Override
    public void draw(GraphicsContext gc) {
        double force = this.calcForce(end1, end2);
        double angle = this.getAngle(end1, end2);
        end1.setAccOnForce(force, angle);
        end2.setAccOnForce(force, angle);
        gc.setStroke(Color.BLUEVIOLET);
        gc.strokeLine(end1.x, end1.y, end2.x, end2.y);
    }

    public double calcForce(MovableObject mo1, MovableObject mo2) {
        double force = G * (mo1.mass * mo2.mass) / (calcDistance(mo1, mo2) * calcDistance(mo1, mo2));
        return force;
    }

    public double getAngle(GameObject go1, GameObject go2) {
        return Math.acos( go2.x - go1.x / calcDistance(go1, go2) );
    }

    public double calcDistance(GameObject go1, GameObject go2) {
        return Math.sqrt( (go1.x - go2.x) * (go1.x - go2.x) + (go1.y - go2.y) * (go1.y - go2.y) );
    }

    @Override
    public boolean intersects(GameObject go) {
        return false;
    }
}
