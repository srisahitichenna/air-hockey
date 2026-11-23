package se.chalmers.team15.game.model;

import se.chalmers.team15.game.model.Model.Position;

/*hi  */
public class Puck implements movable {
    // Variables
    private Model.Position position;
    private Direction direction = new Direction(0, 0);
    private float speed = 0;

    private static final float RADIUS = 30;

    // Making the puck
    public Puck(float xPos, float yPos) {
      this.position = new Position(xPos, yPos);
    }

    // Getters
    public Model.Position getPosition() {
      return position;
    }

    public void setPosition(Model.Position position) {
      this.position = position;
    }

    public Direction getDirection() {
      return direction;
    }

    public void setDirection(Direction direction) {
      this.direction = direction;
    }

    public float getRadius() {
      return RADIUS;
    }

    public float getSpeed() {
      return speed;
    }

    public void setSpeed(float newSpeed) {
      speed = newSpeed;
    }

}
