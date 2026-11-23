package se.chalmers.team15.game.model;

public interface movable {

    // Getters
    public Position getPosition();

    public void setPosition(Position position);

    public Direction getDirection();

    public void setDirection(Direction direction);

    public float getRadius();

    public float getSpeed();

    public void setSpeed(float newspeed);

    public float getWeight();

    public void setWeight(float newWeight);
}
