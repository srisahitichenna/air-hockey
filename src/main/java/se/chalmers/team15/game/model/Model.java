package se.chalmers.team15.game.model;

import java.util.Random;

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

  private int MATCH_LENGTH = 180; // 3 min match length

  private final Rink rink = new Rink(1600, 800, 80);
  private final Puck puck = new Puck(900, 450);
  private final Score score = new Score();
  private final Timer timer = new Timer(MATCH_LENGTH);

  private final Striker[] players = new Striker[2];
  private final movable[] objs = new movable[players.length + 1]; // 1 is number of pucks

  public Model() {
    players[0] = new Striker(new Position(1400, 415));
    players[1] = new Striker(new Position(200, 415));
    objs[0] = players[0];
    objs[1] = players[1];
    objs[2] = puck;
  }

  public Rink getRink() {
    return rink;
  }

  public Puck getPuck() {
    return puck;
  }

  public Score getScore() {
    return score;
  }

  public Striker getPlayer1() {
    return players[0];
  }

  public Striker getPlayer2() {
    return players[1];
  }

  public void goalRight() {
    score.addPlayer1();
  }

  public void goalLeft() {
    score.addPlayer2();
  }

  public Timer getTimer() {
    return timer;
  }

  private Direction[] inputs = new Direction[2];

  public void setInput(float xVelocity, float yVelocity, int strikernr) {
    this.inputs[strikernr] = new Direction(xVelocity, yVelocity);
  }

  /** Advances the simulation by {@code dt} seconds. */
  public void update(
      float dt) { // move -> input -> collision? -> speed&direction // speed&direction kan
    // bestämmas för nästa frame och visas

    /* Timer */
    timer.update(dt);
    if (timer.isFinished()) {
      // stop puck and players
      // announce winner
      return;
    }

    /* Movement */
    moveObjects(objs, dt);
    addInputs();

    /* Wall collision */
    wallcollision(objs);

    /* Striker-Puck collision */
    hitpuck(objs);

    /* Friction */
    friction(objs);

    // put small invisible "striker" on all sharp corners to bounce against
  }

  private void addInputs() {
    for (int i = 0; i < players.length; i++) {
      Direction velocity = new Direction(
          players[i].getDirection().x() * players[i].getSpeed() + inputs[i].x(),
          players[i].getDirection().y() * players[i].getSpeed() + inputs[i].y());
      players[i].setSpeed(velocity.getMagnitudeAndNormalize());
      players[i].setDirection(velocity);
    }
  }

  private <T extends movable> void moveObjects(T[] objects, float dt) {
    for (int i = 0; i < objects.length; i++) { // 1 is number of pucks
      objects[i].setPosition(
          objects[i]
              .getPosition()
              .moveBy(
                  new Direction(
                      objects[i].getDirection().x() * objects[i].getSpeed(),
                      objects[i].getDirection().y() * objects[i].getSpeed()),
                  dt));
    }
  }

  private <T extends movable> void wallcollision(T[] objects) {
    final int topwall = 50;
    final int bottomwall = 850;
    final int leftwall = 100;
    final int rightwall = 1700;
    final int goalsize = 200;
    for (int i = 0; i < objects.length; i++) {
      if (objects[i].getPosition().y() <= topwall + objects[i].getRadius()) {
        objects[i].setDirection(
            new Direction(objects[i].getDirection().x(), Math.abs(objects[i].getDirection().y())));
        objects[i].setPosition(
            new Position(objects[i].getPosition().x(), topwall + objects[i].getRadius()));
      } else if (objects[i].getPosition().y() >= bottomwall - objects[i].getRadius()) {
        objects[i].setDirection(
            new Direction(
                objects[i].getDirection().x(), Math.abs(objects[i].getDirection().y()) * -1));
        objects[i].setPosition(
            new Position(objects[i].getPosition().x(), bottomwall - objects[i].getRadius()));
      }

      if (objects[i].getPosition().x() <= leftwall + objects[i].getRadius()
          && !((objects[i].getPosition().y() < 450 + goalsize / 2
              && objects[i].getPosition().y() > 450 - goalsize / 2)
              && objects[i].getClass() == Puck.class)) {
        objects[i].setDirection(
            new Direction(Math.abs(objects[i].getDirection().x()), objects[i].getDirection().y()));
        objects[i].setPosition(
            new Position(leftwall + objects[i].getRadius(), objects[i].getPosition().y()));
      } else if (objects[i].getPosition().x() >= rightwall - objects[i].getRadius()
          && !((objects[i].getPosition().y() < 450 + goalsize / 2
              && objects[i].getPosition().y() > 450 - goalsize / 2)
              && objects[i].getClass() == Puck.class)) {
        objects[i].setDirection(
            new Direction(
                Math.abs(objects[i].getDirection().x()) * -1, objects[i].getDirection().y()));
        objects[i].setPosition(
            new Position(rightwall - objects[i].getRadius(), objects[i].getPosition().y()));
      }
    }
  }

  private <T extends movable> void hitpuck(T[] strikers) {
    for (int i = 0; i < strikers.length; i++) {
      final float xdiff = strikers[i].getPosition().x() - puck.getPosition().x();
      final float ydiff = strikers[i].getPosition().y() - puck.getPosition().y();

      if (Math.sqrt(xdiff * xdiff + ydiff * ydiff) <= strikers[i].getRadius() + puck.getRadius()) {

        Direction newDirection = new Direction(-xdiff, -ydiff);

        if (newDirection.getMagnitudeAndNormalize() == 0) {
          continue;
        }

        puck.setDirection(newDirection);
        puck.setSpeed(puck.getSpeed() + 70);
      }
    }
  }

  private <T extends movable> void friction(T[] objects) {
    for (int i = 0; i < objects.length; i++) {
      Direction direction = new Direction(
          objects[i].getDirection().x() * objects[i].getSpeed() * 0.997f,
          objects[i].getDirection().y() * objects[i].getSpeed() * 0.997f);
      objects[i].setSpeed(direction.getMagnitudeAndNormalize());
      objects[i].setDirection(direction);

    }
  }

  public <T extends movable> void startMatch() {
    /* Resets score */
    score.resetScore();

    /* Resets puck */
    puck.setPosition(new Position(900, 450));

    /* Resets strikers with random pos */
    Random random = new Random();
    int Player1Pos;
    int Player2Pos;
    Player1Pos = random.nextInt(450, 550);
    Player2Pos = random.nextInt(450, 550);
    getPlayer1().setPosition(new Position(1350, Player2Pos));
    getPlayer2().setPosition(new Position(450, Player1Pos));

    /* Resets timer, and starts timer */
    timer.resetTimer();
    timer.startTimer();
  }

  public Winner getWinner() {
    if ((int) score.getPlayer1Score() == (int) score.getPlayer2Score()) {
      return Winner.DRAW;
    } else if ((int) score.getPlayer1Score() > (int) score.getPlayer2Score()) {
      return Winner.PLAYER1;
    }
    return Winner.PLAYER2;
  }
}
