package Gravityhook.Interfaces;

public interface Movable {
    Movable move(double milis);

    Movable fixCoords(double maxWidth, double maxHeight);

    double getMass();
}
