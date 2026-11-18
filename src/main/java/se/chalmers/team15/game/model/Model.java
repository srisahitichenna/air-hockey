package se.chalmers.team15.game.model;

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

  private final Puck puck = new Puck(900, 450);

  public Puck getPuck() {
    return puck;
  }

  /** In pixels per second. */
  public record Velocity(float x, float y) {}

  private final Score score = new Score();

  public Score getScore() {
    return score;
  }

  public void goalRight() {
    score.addPlayer1();
  }

  public void goalLeft() {
    score.addPlayer2();
  }

  private int MATCH_LENGTH = 300; // 5 min match length
  private final Timer timer = new Timer(MATCH_LENGTH);

  public Timer getTimer() {
    return timer;
  }

  public void startMatch() {
    score.resetScore();
    // reset puck
    // reset strikers
    timer.resetTimer();
    timer.startTimer();
  }

  /** In pixels. */
  public record Size(int width, int height) {}

  private Position position;
  private Velocity velocity;
  private Velocity speed = new Velocity(0, 0);
  private final Size size;

  public Model() {
    this(new Position(1350, 450), new Velocity(0, 0), new Size(80, 80));
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
    timer.update(dt);
    if (timer.isFinished()) {
      // stop puck and players
      // announce winner
      return;
    }
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
