package Gravityhook;

import Gravityhook.Abstract.MovableObject;
import Gravityhook.GameObjects.Mine;
import Gravityhook.GameObjects.Obstacle;
import Gravityhook.GameObjects.Player;
import Gravityhook.GameObjects.Rope;
import Gravityhook.Interfaces.Clickable;
import Gravityhook.Interfaces.Connectable;
import Gravityhook.Interfaces.Drawable;
import Gravityhook.Utils.GameObjectFactory;
import Gravityhook.Utils.Physics;
import Gravityhook.Utils.Routines;
import javafx.application.Platform;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Iterator;

public class Gravityhook {

    private Canvas canvas;

    private ArrayList<Drawable> drawables;

    private ImageCursor cursor;

    private Scene scene;

    public GameController game;

    private Player player;

    private Physics physics;

    private boolean started;

    private GameObjectFactory factory;

    public int score;

    public Gravityhook(Canvas canvas) {
        this.score = 0;
        this.canvas = canvas;
        this.started = false;
        this.factory = new GameObjectFactory();
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
        drawables.addAll(factory.createObjects(Mine.class, 10, canvas.getWidth(),
                canvas.getHeight() - canvas.getHeight() / 2, 0, canvas.getHeight() / 2 - 200));

        this.createPlayer();
        this.game = game;
        new Thread(this::redraw).start();
    }

    private void createPlayer() {
        player = new Player((int)canvas.getWidth() / 2, (int)canvas.getHeight() - 70);
    }


    private void moveCanvasEffect(double rememberedPlayerMovement) {
        if (player.y <= 300 && player.yAcc < 0) {
            player.yAcc = 0;
            if (drawables.size() < 17)
                drawables.add(factory.createRandomObject(canvas.getWidth(), 250, 0, -250));
            drawables.forEach((d) -> {
                if (d instanceof MovableObject) ((MovableObject) d).yAcc = -rememberedPlayerMovement;
            });
        }
    }

    private void redraw() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.GREEN);
        long milis = System.currentTimeMillis();
        Routines r = new Routines();
        double rememberedPlayerMovement;
        double diff;
        int biggestScore = 0;
        mainLoop : do {
            r.sleep(10);
            gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
            diff = (System.currentTimeMillis() - milis) / 10.0;
            if (started)
                physics.apply(player);
            rememberedPlayerMovement = player.yAcc;
            biggestScore += (player.y - (player.y + player.yAcc * diff));
            if (score < biggestScore)
                score = biggestScore;
            moveCanvasEffect(rememberedPlayerMovement);
            player.fixCoords(canvas.getWidth(), canvas.getHeight()).move(diff);
            player.yAcc = rememberedPlayerMovement;
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
                if (d instanceof Obstacle && ((Obstacle) d).intersects(player)) {
                    player.yAcc = 0;
                    for (Drawable mo : drawables) {
                        if (mo instanceof MovableObject) {
                            ((MovableObject) mo).yAcc = 0;
                            ((MovableObject) mo).yAcc = 0;
                        }
                    }
                }
            }
            drawables.removeIf((d) -> d instanceof MovableObject && ((MovableObject) d).y >= canvas.getHeight());
            milis = System.currentTimeMillis();

            gc.setFill(Color.WHITE);
            gc.fillText("Score: " + score, 0, 10);
            gc.setFill(Color.GREEN);
        } while(player.y + player.getHeight() < canvas.getHeight());
        Platform.runLater(game::endGame);
    }

    public void clicked(double sceneX, double sceneY) {
        Iterator<Drawable> it = drawables.iterator();
        while (it.hasNext()) {
            Drawable d = it.next();
            if (d instanceof Connectable) {
                if (((Clickable) d).isClicked((int) sceneX, (int) sceneY)) {
                    ((Clickable) d).setActive(true);
                    started = true;
                    drawables.add(new Rope(player, (MovableObject) d));
                    break;
                }
            }
        }
    }

    public void released() {
        Iterator<Drawable> it = drawables.iterator();
        while (it.hasNext()) {
            Drawable d = it.next();
            if (d instanceof Clickable) {
                if (((Clickable) d).isActive()) {
                    ((Clickable) d).setActive(false);
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
            if (d instanceof Clickable) {
                if (((Clickable) d).isClicked((int) sceneX, (int) sceneY)) {
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
