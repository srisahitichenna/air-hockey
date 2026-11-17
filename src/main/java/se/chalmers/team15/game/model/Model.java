package se.chalmers.team15.game.model;

import se.chalmers.team15.game.model.Model.Direction;

public class Model {
  /** In pixels, measured from the top-left corner of the window. */
  public record Position(float x, float y) {
    public Position moveBy(Direction direction, float dt) {
      return new Position(x + direction.x() * dt, y + direction.y() * dt);
    }
  }

  /** In pixels per second. */
  public record Direction(float x, float y) {
  }

  private final Rink rink = new Rink(1600, 800, 80);

  public Rink getRink() {
    return rink;
  }

  private final Puck puck = new Puck(900, 450);

  public Puck getPuck() {
    return puck;
  }

  private final Score score = new Score();

  public Score getScore() {
    return score;
  }

  public void goalRight() {
    score.addPlayer1();
    ;
  }

  public void goalLeft() {
    score.addPlayer2();
  }

  public void restartGame() {
    score.resetScore();
    // reset puck
    // reset strikers
  }

  /** In pixels. */
  public record Size(int width, int height) {
  }

  private Position position;
  private Direction direction;
  private Direction speed = new Direction(0, 0);
  private final Size size;

  public Model() {
    this(new Position(1350, 450), new Direction(0, 0), new Size(80, 80));
  }

  public Model(Position position, Direction velocity, Size size) {
    this.position = position;
    this.direction = velocity;
    this.size = size;
  }

  /** Advances the simulation by {@code dt} seconds. */
  public void update(float dt) {
    speed = new Direction(speed.x() * 0.99f + direction.x(), speed.y() * 0.99f + direction.y());
    position = position.moveBy(speed, dt);

    if (position.y() < 100 || position.y() > 1000) {

    }

    if (position.x() == 100 || position.x() == 2000) {
      this.direction = new Model.Direction(this.direction.x() * -1, this.direction.y());
    }
  }

  public void setDirection(Direction direction) {
    this.direction = direction;
  }

  public Position getPosition() {
    return position;
  }

  public Direction getDirection() {
    return direction;
  }

  public Size getSize() {
    return size;
  }
}
