package se.chalmers.team15.game.model;

import java.util.Random;

public class Model {
  private int MATCH_LENGTH = 180; // 3 min match length

  private final Rink rink = new Rink(100, 50, 1600, 800, 80);
  final float centerY = rink.getCenterY();

  private final Puck puck = new Puck(900, centerY);
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

/*   public void goalRight() {
    score.addPlayer1();
  }

  public void goalLeft() {
    score.addPlayer2();
  } */

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
    wallCollision(objs);
    checkGoals();

    /* Striker-Puck collision */
    hitPuck(objs);

    /* Friction */
    friction(objs);

    // put small invisible "striker" on all sharp corners to bounce against
  }

  private void addInputs() {
    for (int i = 0; i < players.length; i++) {
      Direction velocity =
          new Direction(
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

  private <T extends movable> void wallCollision(T[] objects) {
    final float topwall = rink.getTop();
    final float bottomwall = rink.getBottom();
    final float leftwall = rink.getLeft();
    final float rightwall = rink.getRight();
    final float middlewall = rink.getCenterX();
    final float centerY = rink.getCenterY();
    final float goalHalf = rink.getGoalHalfHeight();

    for (int i = 0; i < objects.length; i++) {
      float x = objects[i].getPosition().x();
      float y = objects[i].getPosition().y();
      float dirX = objects[i].getDirection().x();
      float dirY = objects[i].getDirection().y();
      float radius = objects[i].getRadius();

      boolean puckInGoal =
        objects[i] instanceof Puck && Math.abs(y - centerY) < goalHalf;

      if (y <= topwall + radius) {
        objects[i].setDirection(new Direction(dirX, Math.abs(dirY)));
        objects[i].setPosition(new Position(x, topwall + radius));
      } else if (y >= bottomwall - radius) {
        objects[i].setDirection(new Direction(dirX, Math.abs(dirY) * -1));
        objects[i].setPosition(new Position(x, bottomwall - radius));
      }

      if (x <= leftwall + radius && !puckInGoal) {
        objects[i].setDirection(new Direction(Math.abs(dirX), dirY));
        objects[i].setPosition(new Position(leftwall + radius, y));
      } else if (x >= rightwall - radius && !puckInGoal) {
        objects[i].setDirection(new Direction(Math.abs(dirX) * -1, dirY));
        objects[i].setPosition(new Position(rightwall - radius, y));
      }

      if (x >= middlewall - radius && objects[i].equals(getPlayer2())) {
        objects[i].setDirection(new Direction(Math.abs(dirX) * -1, dirY));
        objects[i].setPosition(new Position(middlewall - radius, y));
      } else if (x <= middlewall + radius && objects[i].equals(getPlayer1())) {
        objects[i].setDirection(new Direction(Math.abs(dirX), dirY));
        objects[i].setPosition(new Position(middlewall + radius, y));
      }
    }
  }

  private <T extends movable> void hitPuck(T[] strikers) {
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

  private void checkGoals() {
    Rink.GoalSide goal =
        rink.checkGoal(
            puck.getPosition()); // use getPuck().getPosition() if you have no `puck` field
    if (goal == Rink.GoalSide.NONE) return;

    if (goal == Rink.GoalSide.LEFT) score.addPlayer2(); // right player scored
    else score.addPlayer1(); // left player scored

    puck.setPosition(new Position(rink.getCenterX(), rink.getCenterY()));
    puck.setSpeed(0);
  }

  private <T extends movable> void friction(T[] objects) {
    for (int i = 0; i < objects.length; i++) {
      Direction direction =
          new Direction(
              objects[i].getDirection().x() * objects[i].getSpeed() * 0.997f,
              objects[i].getDirection().y() * objects[i].getSpeed() * 0.997f);
      objects[i].setSpeed(direction.getMagnitudeAndNormalize());
      objects[i].setDirection(direction);
    }
  }

  public void startMatch() {
    /* Resets score */
    score.resetScore();

    final float centerY = rink.getCenterY();

    /* Resets puck */
    puck.setPosition(new Position(rink.getCenterX(), centerY));
    puck.setSpeed(0);
    // reset speed of strikers?

    /* Resets strikers with random pos */
    Random random = new Random();
    float y1 = centerY + random.nextInt(-50, 50);
    float y2 = centerY + random.nextInt(-50, 50);
    float quarter = rink.getWidth() / 4f;
    getPlayer1().setPosition(new Position(rink.getRight() - quarter, y1));
    getPlayer2().setPosition(new Position(rink.getLeft() + quarter, y2));

    /* Resets timer, and starts timer */
    timer.resetTimer();
    timer.startTimer();
  }

  public Winner getWinner() {
    if (score.getPlayer1Score() == score.getPlayer2Score()) {
      return Winner.DRAW;
    } else if (score.getPlayer1Score() > score.getPlayer2Score()) {
      return Winner.PLAYER1;
    }
    return Winner.PLAYER2;
  }
}
