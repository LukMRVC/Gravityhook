package Gravityhook.GameObjects;

import Gravityhook.Abstract.GameObject;
import Gravityhook.Abstract.MovableObject;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class Rope extends GameObject {
    public static final double G = 0.06674;          //Gravitational constant, same for everything

    public Player player;

    public Mine mine;

    public double force;

    public Rope(int x, int y) {
        super(x, y);
    }

    public Rope(Player go1, Mine go2) {
        super(go1.x, go1.y);
        player = go1;
        mine = go2;

    }

    @Override
    public void draw(GraphicsContext gc) {
        calcForce();
        gc.setStroke(Color.BLUEVIOLET);
        gc.strokeLine(player.x, player.y, mine.x, mine.y);
    }

    private void calcForce() {
        double force =  (player.mass * mine.mass) / (calcDistance(player, mine) * calcDistance(player, mine));
        player.xAcc = force - (G * player.mass);
        player.yAcc = force - (G * player.mass);
        mine.xAcc = -force - (G * mine.mass);
        mine.yAcc = -force - (G * mine.mass);
    }

    private double calcDistance(GameObject go1, GameObject go2) {
        return Math.sqrt( (go1.x - go2.x) * (go1.x - go2.x) + (go1.y - go2.y) * (go1.y - go2.y) );
    }

    @Override
    public boolean intersects(GameObject go) {
        return false;
    }
}
