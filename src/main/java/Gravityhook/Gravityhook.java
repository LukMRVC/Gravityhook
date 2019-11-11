package Gravityhook;

import Gravityhook.Abstract.GameObject;
import Gravityhook.Abstract.MovableObject;
import Gravityhook.GameObjects.Mine;
import Gravityhook.GameObjects.Player;
import Gravityhook.GameObjects.Rope;
import Gravityhook.Interfaces.Drawable;
import gherkin.lexer.Pl;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Gravityhook {

    private Canvas canvas;

    private ArrayList<Drawable> drawables;

    private ImageCursor cursor;

    private Scene scene;

    private Player player;

    private Physics physics;

    private boolean started;

    public Gravityhook(Canvas canvas) {
        this.canvas = canvas;
        this.started = false;
        this.drawables = new ArrayList<>();
        try {
            this.cursor = new ImageCursor(new Image(getClass().getClassLoader().getResourceAsStream("img/cursor.png")));
        } catch (NullPointerException ex) {
            this.cursor = null;
            System.out.println(ex);
            throw ex;
        }

    }

    public void game(GameController game) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.GREEN);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        physics = new Physics();
        this.createMines();
        this.createPlayer();

        new Thread(this::redraw).start();

    }

    private void createPlayer() {
        player = new Player((int)canvas.getWidth() / 2, (int)canvas.getHeight() - 70);
        drawables.add(player);
    }

    private void createMines() {
        for (int i = 0; i < 5; ++i) {
            drawables.add(new Mine(new Random().nextInt(380), new Random().nextInt(500)));
        }
    }

    private void redraw() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.GREEN);
        long milis = System.currentTimeMillis();
        Routines r = new Routines();
        while(true) {
            r.sleep(10);
            gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
            double diff = (System.currentTimeMillis() - milis) / 10.0;
            for (Drawable d : drawables) {
                d.draw(gc);
                if (d instanceof MovableObject) {
                    if (d instanceof Player && started)
                        physics.apply((MovableObject) d);
                    ((MovableObject) d).move(diff);

                }
            }
            milis = System.currentTimeMillis();
        }

    }

    public void clicked(double sceneX, double sceneY) {
        Iterator<Drawable> it = drawables.iterator();
        while (it.hasNext()) {
            Drawable d = it.next();
            if (d instanceof Mine) {
                if (((Mine) d).isClicked((int)sceneX, (int)sceneY)) {
                    ((Mine) d).setActive(true);
                    started = true;
                    drawables.add(new Rope(player, (Mine) d));
                    break;
                }
            }
        }
    }

    public void released() {
        Iterator<Drawable> it = drawables.iterator();
        while (it.hasNext()) {
            Drawable d = it.next();
            if (d instanceof Mine) {
                if (((Mine) d).isActive()) {
                    ((Mine) d).setActive(false);
                }
            }
            if (d instanceof Rope) {
                it.remove();
            }
        }
    }

    public void moved(double sceneX, double sceneY) {
        Iterator<Drawable> it = drawables.iterator();
        boolean isOn = false;
        while (it.hasNext()) {
            Drawable d = it.next();
            if (d instanceof Mine) {
                if (((Mine) d).isClicked((int) sceneX, (int) sceneY)) {
                    isOn = true;
                    scene.setCursor(this.cursor);
                    break;
                }
            }
        }
        if (!isOn) {
            scene.setCursor(Cursor.DEFAULT);
        }
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }
}
