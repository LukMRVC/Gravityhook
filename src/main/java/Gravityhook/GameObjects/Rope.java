package Gravityhook.GameObjects;

import Gravityhook.Abstract.GameObject;
import Gravityhook.Abstract.MovableObject;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Rope extends GameObject {
    public static final double G = 0.003674;          //Gravitational constant, same for everything

    public MovableObject end1; //player

    public MovableObject end2; //mine

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
        end1.setAccOnForce(force, getAngle(end1, end2));
        end2.setAccOnForce(force / 4.0, getAngle(end2, end1));
        gc.setStroke(Color.BLUEVIOLET);
        gc.strokeLine(end1.x, end1.y, end2.x, end2.y);
    }

    public double calcForce(MovableObject mo1, MovableObject mo2) {
        return G * (mo1.getMass() * mo2.getMass()) / (calcDistance(mo1, mo2) );
    }

    public double getAngle(GameObject center, GameObject point) { //center = player
        final double deltaX = point.x - center.x;
        final double deltaY = point.y - center.y;
        double angle = Math.atan2( deltaY, deltaX );
        return angle;
    }

    public double calcDistance(GameObject go1, GameObject go2) {
        return Math.sqrt( (go1.x - go2.x) * (go1.x - go2.x) + (go1.y - go2.y) * (go1.y - go2.y) );
    }

    @Override
    public boolean intersects(GameObject go) {
        return false;
    }

    @Override
    public double getWidth() {
        return Math.abs(this.end1.x - this.end2.x);
    }

    @Override
    public double getHeight() {
        return Math.abs(this.end1.y - this.end2.y);
    }
}
