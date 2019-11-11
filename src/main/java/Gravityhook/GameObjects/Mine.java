package Gravityhook.GameObjects;

import Gravityhook.Abstract.GameObject;
import Gravityhook.Abstract.MovableObject;
import Gravityhook.Interfaces.Clickable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class Mine extends MovableObject implements Clickable {
    public final double mass = 50;

    private Image img;

    private Image imgActive;

    private boolean active;

    public Mine(int x, int y) {
        super(x, y);
        active = false;
        try {
            this.img = new Image(getClass().getClassLoader().getResourceAsStream("img/mine.png"));
            this.imgActive = new Image(getClass().getClassLoader().getResourceAsStream("img/mine-spiked.png"));
        } catch (NullPointerException ex) {
            System.out.println("Error while loading mine images.");
            System.out.println(ex);
            System.exit(1);
        }

    }

    @Override
    public void draw(GraphicsContext gc) {
        if (active)
            gc.drawImage(this.imgActive, x - (imgActive.getWidth() / 2), y - (imgActive.getHeight() / 2));
        else
            gc.drawImage(this.img,  x - (img.getWidth() / 2), y - (img.getHeight() / 2));
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
        if (active == false) {
            this.xAcc = 0;
            this.yAcc = 0;
        }
    }

    @Override
    public boolean intersects(GameObject go) {
        return false;
    }

    @Override
    public boolean isClicked(int x, int y) {
        if ( Math.abs(this.x - x) < this.img.getWidth() && Math.abs(this.y - y) < this.img.getHeight() ) {
            return true;
        }
        return false;
    }

    public void setAccOnForce(double force, double angle) {
        this.xAcc += Math.cos(angle) * force * mass;
        this.yAcc += Math.sin(angle) * force * mass;
    }

    public void move(double milis) {
        x += xAcc * milis;
        y += yAcc * milis;
    }

}
