package Gravityhook.GameObjects;

import Gravityhook.Abstract.GameObject;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class Rope extends GameObject {

    public GameObject start;

    public GameObject end;

    public Rope(int x, int y) {
        super(x, y);
    }

    public Rope(GameObject go1, GameObject go2) {
        super(go1.x, go1.y);
        start = go1;

        end = go2;
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setStroke(Color.BLUEVIOLET);
        gc.strokeLine(start.x, start.y, end.x, end.y);
    }

    @Override
    public boolean intersects(GameObject go) {
        return false;
    }
}
