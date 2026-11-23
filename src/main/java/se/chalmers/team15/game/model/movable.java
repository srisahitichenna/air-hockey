package se.chalmers.team15.game.model;

public interface movable {

    // Getters
    public Model.Position getPosition();

    public void setPosition(Model.Position position);

    public Direction getDirection();

    public void setDirection(Direction direction);

    public float getRadius();

    public float getSpeed();
}
