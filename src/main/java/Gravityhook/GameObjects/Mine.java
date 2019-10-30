package Gravityhook.GameObjects;

import Gravityhook.Abstract.GameObject;
import Gravityhook.Abstract.MovableObject;
import javafx.scene.canvas.GraphicsContext;

public class Mine extends MovableObject {

    public Mine(int x, int y) {
        super(x, y);
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.fillOval(x, y, 20, 20);
    }

    @Override
    public boolean intersects(GameObject go) {
        return false;
    }
}
