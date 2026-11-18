package se.chalmers.team15.game.model;

import se.chalmers.team15.game.model.Model.Position;
import se.chalmers.team15.game.model.Model.Size;
import se.chalmers.team15.game.model.Model.Velocity;

public class Striker {
  private Position position;
  private Velocity velocity = new Velocity(0, 0);
  private Velocity speed = new Velocity(0, 0);
  private final Size size;

  public Striker(Position position, Size size) {
    this.position = position;
    this.size = size;
  }

  public void update(float dt) {
    speed = new Velocity(speed.x() * 0.99f + velocity.x(), speed.y() * 0.99f + velocity.y());
    position = position.moveBy(speed, dt);
  }

  public void setVelocity(Velocity velocity) {
    this.velocity = velocity;
  }

  public Position getPosition() {
    return position;
  }

  public Size getSize() {
    return size;
  }

  public Velocity getVelocity() {
    return velocity;
  }
}
