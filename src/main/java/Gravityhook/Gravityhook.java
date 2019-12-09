package Gravityhook;

import Gravityhook.Abstract.MovableObject;
import Gravityhook.GameObjects.Mine;
import Gravityhook.GameObjects.Obstacle;
import Gravityhook.GameObjects.Player;
import Gravityhook.GameObjects.Rope;
import Gravityhook.Interfaces.Drawable;
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

    public int score;

    public Gravityhook(Canvas canvas) {
        this.score = 0;
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
        this.createMines(10);
        this.createPlayer();
        this.game = game;
        this.drawables.add(new Obstacle(50, 250));
        new Thread(this::redraw).start();
    }

    private void createPlayer() {
        player = new Player((int)canvas.getWidth() / 2, (int)canvas.getHeight() - 70);
    }

    private void createMines(int count) {
        for (int i = 0; i < count / 2; ++i) {
            drawables.add(this.createMine(new Random().nextInt(380), (int) (canvas.getHeight() / 2 + new Random().nextInt(200))));
        }
        for (int i = 0; i < count / 2 + 1; ++i) {
            drawables.add(this.createMine(new Random().nextInt(380), 20 + new Random().nextInt((int) canvas.getHeight() / 2) ));
        }
    }

    private Mine createMine() {
        Mine m = new Mine(new Random().nextInt((int)canvas.getWidth() - 20), -250 + new Random().nextInt(230));
        return m;
    }

    private Mine createMine(int x, int y) {
        Mine m = new Mine(x, y);
        return m;
    }

    private Drawable createGameObject() {
        if (new Random().nextInt(100) > 80) {
            return new Obstacle(new Random().nextInt((int) canvas.getWidth() - 20), -250 + new Random().nextInt(230));
        }
        return createMine();
    }

    private void moveCanvasEffect(double rememberedPlayerMovement) {
        if (player.y <= 300 && player.yAcc < 0) {
            player.yAcc = 0;
            if (drawables.size() < 17)
                drawables.add(createGameObject());
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
