package Gravityhook;

import Gravityhook.Abstract.GameObject;
import Gravityhook.Abstract.MovableObject;
import Gravityhook.GameObjects.Mine;
import Gravityhook.GameObjects.Player;
import Gravityhook.GameObjects.Rope;
import Gravityhook.Interfaces.Drawable;
import Gravityhook.Interfaces.IHitbox;
import gherkin.lexer.Pl;
import javafx.application.Platform;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import org.graalvm.compiler.nodes.memory.ReadNode;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Gravityhook {

    private Canvas canvas;

    private ArrayList<Drawable> drawables;

    private ImageCursor cursor;

    private Scene scene;

    public GameController game;

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
        this.game = game;
        new Thread(this::redraw).start();
    }

    private void createPlayer() {
        player = new Player((int)canvas.getWidth() / 2, (int)canvas.getHeight() - 70);
    }

    private void createMines() {
        for (int i = 0; i < 5; ++i) {
            drawables.add(new Mine(new Random().nextInt(380), canvas.getHeight() / 2 + new Random().nextInt(200)));
        }
    }

    private void redraw() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.GREEN);
        long milis = System.currentTimeMillis();
        Routines r = new Routines();
        mainLoop : do {
            r.sleep(10);
            gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
            double diff = (System.currentTimeMillis() - milis) / 10.0;
            if (started)
                physics.apply(player);
            player.move(diff).fixCoords(canvas.getWidth(), canvas.getHeight());
            player.draw(gc);
            for (Drawable d : drawables) {
                d.draw(gc);
                if (d instanceof MovableObject) {
                    ((MovableObject) d).fixCoords(canvas.getWidth(), canvas.getHeight())
                            .move(diff);
                }
                if ( d instanceof Mine && ((Mine) d ).intersects(player) ) {
                    break mainLoop;
                }
            }
            milis = System.currentTimeMillis();
        } while(player.y + player.getHeight() < canvas.getHeight());
        Platform.runLater(game::endGame);
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
