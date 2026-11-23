package se.chalmers.team15.game.model;

public class Model {

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
    wallCollision(objs);

    /* Striker-Puck collision */
    hitPuck(objs);

    // put small invisible "striker" on all sharp corners to bounce against
  }

  private void addInputs() {
    for (int i = 0; i < players.length; i++) {
      Direction velocity =
          new Direction(
              players[i].getDirection().x() * players[i].getSpeed() * 0.99f + inputs[i].x(),
              players[i].getDirection().y() * players[i].getSpeed() * 0.99f + inputs[i].y());
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

  public void startMatch() {
    score.resetScore();
    // reset puck
    // reset strikers
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
