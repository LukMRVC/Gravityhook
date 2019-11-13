package Gravityhook.Interfaces;

public interface Movable {
    Movable move(double milis);

    void setAccOnForce(double force, double angle);

    Movable fixCoords(double maxWidth, double maxHeight);

    double getMass();
}
