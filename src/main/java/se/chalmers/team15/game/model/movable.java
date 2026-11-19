package se.chalmers.team15.game.model;

import se.chalmers.team15.game.model.Model.Direction;

public interface movable {

    // Getters
    public Model.Position getPosition();

    public void setPosition(Model.Position position);

    public Model.Direction getDirection();

    public void setDirection(Direction direction);

    public float getRadius();

    public float getSpeed();
}
