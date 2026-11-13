package se.chalmers.team15.game.model;

import java.util.Vector;

import com.raylib.Raylib.Vector2;

import se.chalmers.team15.game.model.Model.Velocity;

public class Model {
  /** In pixels, measured from the top-left corner of the window. */
  public record Position(float x, float y) {
    public Position moveBy(Velocity velocity, float dt) {
      return new Position(x + velocity.x() * dt, y + velocity.y() * dt);
    }
  }

  private final Rink rink = new Rink(1600, 800, 80);

  public Rink getRink() {
    return rink;
  }

  /** In pixels per second. */
  public record Velocity(float x, float y) {}

  /** In pixels. */
  public record Size(int width, int height) {}

  private Position position;
  private Velocity velocity;
  private Velocity speed = new Velocity(0, 0);
  private final Size size;

  public Model() {
    this(new Position(100, 100), new Velocity(0, 0), new Size(80, 80));
  }

  public Model(Position position, Velocity velocity, Size size) {
    this.position = position;
    this.velocity = velocity;
    this.size = size;
  }

  /** Advances the simulation by {@code dt} seconds. */
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

  public Velocity getVelocity() {
    return velocity;
  }

  public Size getSize() {
    return size;
  }
}
