package se.chalmers.team15.game.model;

import se.chalmers.team15.game.model.Model.Position;
import se.chalmers.team15.game.model.Model.Direction;

public class Striker implements movable {
  private Position position;
  private Direction direction = new Direction(0, 0);
  private float speed = 0;
  private float weight = 100;

  private static final float RADIUS = 50;

  public Striker(Position position) {
    this.position = position;
  }

  public void setPosition(Model.Position position) {
    this.position = position;
  }

  public void setDirection(Direction direction) {
    this.direction = direction;
  }

  public float getSpeed() {
    return speed;
  }

  public void setSpeed(float newSpeed) {
    speed = newSpeed;
  }

  public Position getPosition() {
    return position;
  }

  public float getWeight() {
    return weight;
  }

  public void setWeight(float newWeight) {
    weight = newWeight;
  }

  public float getRadius() {
    return RADIUS;
  }

  public Direction getDirection() {
    return direction;
  }
}
