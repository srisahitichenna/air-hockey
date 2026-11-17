package se.chalmers.team15.game.model;

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

  private Direction direction;

  public Model() {
    this(new Direction(0, 0));
  }

  public Model(Direction direction) {
    this.direction = direction;
  }

  /** Advances the simulation by {@code dt} seconds. */
  public void update(float dt) {
    puck.setPosition(puck.getPosition().moveBy(puck.getDirection(), puck.getSpeed()));
    puck.setSpeed();
    puck.setDirection();
    //speed = new Direction(speed.x() * 0.99f + direction.x(), speed.y() * 0.99f + direction.y());

    if (puck.getPosition().y() < 100 || puck.getPosition().y() > 1000) {
      puck.setDirection(puck.getDirection().x(), puck.getDirection().y() * -1);
    }

    if (puck.getPosition().x() == 100 || puck.getPosition().x() == 2000) {
      puck.setDirection(puck.getDirection().x() * -1, puck.getDirection().y());
    }
  }

  public void setDirection(Direction direction) {
    this.direction = direction;
  }
}
