package se.chalmers.team15.game.model;

public class Striker implements movable {
  private Position position;
  private Direction direction = new Direction(0, 0);
  private float speed = 0;
  
  private static final float RADIUS = 50;

  public Striker(Position position) {
    this.position = position;
  }
  
  public void setPosition(Position position) {
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

  public float getRadius() {
    return RADIUS;
  }

  public Direction getDirection() {
    return direction;
  }
}
