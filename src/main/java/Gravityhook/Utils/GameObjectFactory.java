package Gravityhook.Utils;

import Gravityhook.Abstract.GameObject;
import Gravityhook.GameObjects.Graviton;
import Gravityhook.GameObjects.Mine;
import Gravityhook.GameObjects.Obstacle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameObjectFactory {

    public <T extends GameObject> GameObject createObject(Class<T> clz, double boundX, double boundY,
                                                          double fromX, double fromY) {
        GameObject g = null;
        try {
            g = clz.getDeclaredConstructor(double.class, double.class).newInstance(
                    (fromX + new Random().nextInt((int) boundX)),
                    fromY + new Random().nextInt((int) boundY));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return g;
    }

    public <T extends GameObject> GameObject createObject(Class<T> clz,
                                                          double boundX, double boundY) {
        return createObject(clz, boundX, boundY, 0, 0);
    }

    public GameObject createRandomObject(double boundX, double boundY) {
        int rand = new Random().nextInt(100);
        if (rand > 95) {
            return this.createObject(Graviton.class, boundX, boundY);
        } else if (rand > 85) {
            return createObject(Obstacle.class, boundX, boundY);
        }
        return createObject(Mine.class, boundX, boundY);
    }

    public GameObject createRandomObject(double boundX, double boundY, double fromX, double fromY) {
        int rand = new Random().nextInt(100);
        if (rand > 95) {
            return this.createObject(Graviton.class, boundX, boundY, fromX, fromY);
        } else if (rand > 85) {
            return createObject(Obstacle.class, boundX, boundY, fromX, fromY);
        }
        return createObject(Mine.class, boundX, boundY, fromX, fromY);
    }

    public <T extends GameObject> List<GameObject> createObjects(Class<T> clz, int count, double boundX, double boundY) {
        List<GameObject> objects = new ArrayList<>();
        for (int i = 0; i < count; ++i) {
            objects.add(createObject(clz, boundX, boundY));
        }
        return objects;
    }

    public List<GameObject> createRandomObjects(int count, double boundX, double boundY) {
        List<GameObject> objects = new ArrayList<>();
        for (int i = 0; i < count; ++i) {
            objects.add(createRandomObject(boundX, boundY));
        }
        return objects;
    }

    public List<GameObject> createRandomObjects(int count, double boundX, double boundY, double fromX, double fromY) {
        List<GameObject> objects = new ArrayList<>();
        for (int i = 0; i < count; ++i) {
            objects.add(createRandomObject(boundX, boundY, fromX, fromY));
        }
        return objects;
    }


    public List<GameObject> createObjects(Class<Mine> clz, int count, double boundX, double boundY, double fromX, double fromY) {
        List<GameObject> objects = new ArrayList<>();
        for (int i = 0; i < count; ++i) {
            objects.add(createObject(clz, boundX, boundY, fromX, fromY));
        }
        return objects;
    }
}
