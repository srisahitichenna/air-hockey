package se.chalmers.team15.game.model;

public class Model {
  /** In pixels, measured from the top-left corner of the window. */
  public record Position(float x, float y) {
    public Position moveBy(Direction velocity, float dt) {
      return new Position(this.x + velocity.x() * dt, this.y + velocity.y() * dt);
    }
  }

  /** In pixels per second. */
  public static class Direction {
    private float x;
    private float y;

    public Direction(float x, float y) {
      this.x = x;
      this.y = y;
    }

    public float getMagnitudeAndNormalize() {
      if (this.x == 0 && this.y == 0) {
        return 0;
      }
      float magnitude = (float) Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2));
      this.x = x / magnitude;
      this.y = y / magnitude;
      return magnitude;
    }

    public float x() {
      return this.x;
    }

    public float y() {
      return this.y;
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
  public void update(float dt) { // move -> input -> collision? -> speed&direction // speed&direction kan
                                 // bestämmas för nästa frame och visas
    puck.setPosition(puck.getPosition().moveBy(
        new Direction(puck.getDirection().x() * puck.getSpeed(), puck.getDirection().y() * puck.getSpeed()), dt));

    Direction velocity = new Direction(puck.getDirection().x() * puck.getSpeed() * 0.99f + direction.x(),
        puck.getDirection().y() * puck.getSpeed() * 0.99f + direction.y());
    puck.setSpeed(velocity.getMagnitudeAndNormalize());
    puck.setDirection(velocity);

    /* Puck-Wall collision */
    wallcollision(puck);

    /*
     * To Be Implemented when Striker class appears
     * Striker-Wall collision
     * for each Striker {
     * wallcollision(Striker);
     * }
     */
    /* Striker-Puck collision */

    //put small invisible "striker" on all sharp corners to bounce against

  }

  public <T extends movable> void wallcollision(T object) {
    final int topwall = 50;
    final int bottomwall = 850;
    final int leftwall = 100;
    final int rightwall = 1700;
    final int goalsize = 200;

    if (object.getPosition().y() <= topwall + object.getRadius()){
      object.setDirection(new Direction(object.getDirection().x(), Math.abs(object.getDirection().y())));
      object.setPosition(new Position(object.getPosition().x(), topwall + object.getRadius()));
    }
    else if (object.getPosition().y() >= bottomwall - object.getRadius()){
      object.setDirection(new Direction(object.getDirection().x(), Math.abs(object.getDirection().y()) * -1));
      object.setPosition(new Position(object.getPosition().x(), bottomwall - object.getRadius()));
    }

    if (object.getPosition().x() <= leftwall + object.getRadius() && !((object.getPosition().y() < 450 + goalsize / 2 && object.getPosition().y() > 450 - goalsize / 2) && object.getClass() == Puck.class)){
      object.setDirection(new Direction(Math.abs(object.getDirection().x()), object.getDirection().y()));
      object.setPosition(new Position(leftwall + object.getRadius(), object.getPosition().y()));
    }
    else if (object.getPosition().x() >= rightwall - object.getRadius() && !((object.getPosition().y() < 450 + goalsize / 2 && object.getPosition().y() > 450 - goalsize / 2) && object.getClass() == Puck.class)){
      object.setDirection(new Direction(Math.abs(object.getDirection().x()) * -1, object.getDirection().y()));
      object.setPosition(new Position(rightwall - object.getRadius(), object.getPosition().y()));
    }

  }

  public void setDirection(Direction direction) {
    this.direction = direction;
  }
}
