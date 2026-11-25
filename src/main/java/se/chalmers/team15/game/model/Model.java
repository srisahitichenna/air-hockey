package se.chalmers.team15.game.model;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Random;

public class Model {

  private int MATCH_LENGTH = 180; // 3 min match length
  private float Friktionskoefficient = 0.99f;

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
    //moveObjects(objs, dt);
    addInputs();
    accurateCollisionsAndMovementWithFriction(dt);
    /* Wall collision */
    //wallCollision(objs);

    /* Striker-Puck collision */
    //hitPuck(objs);

    /* Friction */
    //friction(objs);

    // put small invisible "striker" on all sharp corners to bounce against
  }

  private void addInputs() {
    for (int i = 0; i < players.length; i++) {
      float dirX = players[i].getDirection().x();
      float dirY = players[i].getDirection().y();
      float speed = players[i].getSpeed();
      Direction velocity = new Direction(dirX * speed + inputs[i].x(), dirY * speed + inputs[i].y());
      players[i].setSpeed(velocity.getMagnitudeAndNormalize());
      players[i].setDirection(velocity);
    }
  }

  private void moveObjects(double dt) {
    for (int i = 0; i < objs.length; i++) {
      Position pos = objs[i].getPosition();
      float dirX = objs[i].getDirection().x();
      float dirY = objs[i].getDirection().y();
      float speed = objs[i].getSpeed();
      objs[i].setPosition(pos.moveBy(new Direction(dirX * speed, dirY * speed), (float)dt));
    }
  }

  private void accurateCollisionsAndMovementWithFriction(float dt){
    record Collisionpair(movable o1, movable o2, Position pos, Float xory, Collision collisiontype, double percentage){}
    PriorityQueue<Collisionpair> pqueue = new PriorityQueue<>(Comparator.comparingDouble(entry -> entry.percentage()));
    movable[] movingobjs = objs;
    Position[] stationarycoords = new Position[4];
    float[] stationaryverticalwalls = new float[3];
    float[] stationaryhorizontalwalls = new float[2];
    //change to real values
    stationarycoords[0] = new Position(100, 350);//left top goal corner
    stationarycoords[1] = new Position(1700, 550);//left bottom goal corner
    stationarycoords[2] = new Position(100, 350);//right top goal corner
    stationarycoords[3] = new Position(1700, 550);//right bottom goal corner
    stationaryverticalwalls[0] = 100;//leftwall
    stationaryverticalwalls[1] = 900;//middlewall
    stationaryverticalwalls[2] = 1700;//rightwall
    stationaryhorizontalwalls[0] = 50;//topwall
    stationaryhorizontalwalls[1] = 850;//bottomwall
    //
    double maxpercentage = 1;
    while(true) {
      for(int i = 0; i < movingobjs.length; i++){
        for(int j = 0; j < movingobjs.length && i < j; i++){
          //Vars
          Direction v2 = movingobjs[j].getDirection();
          float v2x = v2.x();
          float v2y = v2.y();
          float m2 = movingobjs[j].getSpeed();
          Direction v = movingobjs[i].getDirection();
          float vx = v.x();
          float vy = v.y();
          float m = movingobjs[i].getSpeed();
          float x3 = movingobjs[j].getPosition().x();
          float y3 = movingobjs[j].getPosition().y();
          float x1 = movingobjs[i].getPosition().x();
          float y1 = movingobjs[i].getPosition().y();
          float r1 = movingobjs[i].getRadius();
          float r2 = movingobjs[j].getRadius();
          //Calc
          double A = Math.pow((v2x*m2 - vx*m), 2) + Math.pow((v2y*m2 - vy*m), 2);
          double B = 2*(x3 - x1)*(v2x*m2 - vx*m) + 2*(y3 - y1)*(v2y*m2 - vy*m);
          double C = Math.pow((x3 - x1), 2) + Math.pow((y3 - y1), 2) - Math.pow((r1 + r2), 2);
          double a1 = (-B + Math.sqrt(Math.pow(B, 2) - 4 * A * C)) / (2 * A);
          double a2 = (-B - Math.sqrt(Math.pow(B, 2) - 4 * A * C)) / (2 * A);
          if (a1 <= maxpercentage && a1 >= 0) {
            if (a2 <= maxpercentage && a2 >= 0)
              pqueue.add(new Collisionpair(movingobjs[i], movingobjs[j], null, null, Collision.STATIONARY, a1 < a2 ? a1 : a2));
            else
              pqueue.add(new Collisionpair(movingobjs[i], movingobjs[j], null, null, Collision.STATIONARY, a1));
          } else if (a2 <= maxpercentage && a2 >= 0)
            pqueue.add(new Collisionpair(movingobjs[i], movingobjs[j], null, null, Collision.STATIONARY, a2));
        }
        for(int j = 0; j < stationarycoords.length; i++){
          //Vars
          float x1 = movingobjs[i].getPosition().x();
          float y1 = movingobjs[i].getPosition().y();
          if(x1 > stationaryverticalwalls[0] && x1 < stationaryverticalwalls[2] && y1 > stationarycoords[0].y() && y1 < stationarycoords[1].y()){
            Direction v = movingobjs[i].getDirection();
            float vx = v.x();
            float vy = v.y();
            float m = movingobjs[i].getSpeed();
            float x3 = stationarycoords[j].x();
            float y3 = stationarycoords[j].y();
            float r1 = movingobjs[i].getRadius();
            //Calc
            double A = Math.pow(m, 2) * (Math.pow(vx, 2) + Math.pow(vy, 2));
            double B = -2 * m * (vx * (x3 - x1) + vy * (y3 - y1));
            double C = Math.pow((x3 - x1), 2) + Math.pow((y3 - y1), 2) - Math.pow((r1), 2);
            double a1 = (-B + Math.sqrt(Math.pow(B, 2) - 4 * A * C)) / (2 * A);
            double a2 = (-B - Math.sqrt(Math.pow(B, 2) - 4 * A * C)) / (2 * A);
            if (a1 <= maxpercentage && a1 >= 0){
              if (a2 <= maxpercentage && a2 >= 0)
                pqueue.add(new Collisionpair(movingobjs[i], null, stationarycoords[j], null, Collision.STATIONARY, a1 < a2 ? a1 : a2));
              else
                pqueue.add(new Collisionpair(movingobjs[i], null, stationarycoords[j], null, Collision.STATIONARY, a1));
            }
            else if (a2 <= maxpercentage && a2 >= 0)
              pqueue.add(new Collisionpair(movingobjs[i], null, stationarycoords[j], null, Collision.STATIONARY, a2));
          }
        }
        for(int j = 0; j < stationaryverticalwalls.length && !(i == 2 && j == 1); i++){
          float y1 = movingobjs[i].getPosition().x();
          if (!(movingobjs[i].getClass() == Puck.class && y1 > stationarycoords[0].y() && y1 < stationarycoords[1].y())){
            float m = movingobjs[i].getSpeed();
            float vx = movingobjs[i].getDirection().x();
            float r = movingobjs[i].getRadius();
            float x1 = movingobjs[i].getPosition().x();
            float wx = stationaryverticalwalls[j];
            float a = (wx - x1 - r) / (vx * m);
            if (a <= maxpercentage && a >= 0)
              pqueue.add(new Collisionpair(movingobjs[i], null, null, stationaryverticalwalls[j], Collision.VERTWALL, a));
          }
        }
        for(int j = 0; j < stationaryhorizontalwalls.length; i++){
          float m = movingobjs[i].getSpeed();
          float vy = movingobjs[i].getDirection().y();
          float r = movingobjs[i].getRadius();
          float y1 = movingobjs[i].getPosition().y();
          float wy = stationaryhorizontalwalls[j];
          float a = (wy - y1 - r) / (vy * m);
          if (a <= maxpercentage && a >= 0)
            pqueue.add(new Collisionpair(movingobjs[i], null, null, stationaryhorizontalwalls[j], Collision.VERTWALL, a));
        }
      }
      if (pqueue.isEmpty())
        break;
      Collisionpair pair = pqueue.remove();
      double a = pair.percentage();
      maxpercentage -= a;
      moveObjects(dt * a);
      switch(pair.collisiontype()){
        case MOOVING:
          //Two moovers hitting
          break;
        case STATIONARY:
          //moover hitting corner
          break;
        case VERTWALL:
          //moover hitting vertical wall
          break;
        case HORIZWALL:
          //moover hitting horizontal wall
          break;
      }
    }
    moveObjects(dt * maxpercentage);
    friction();
  }

  private <T extends movable> void wallCollision(T[] objects) {
    final int topwall = 50;
    final int middlewall = 900;
    final int bottomwall = 850;
    final int leftwall = 100;
    final int rightwall = 1700;
    final int goalsize = 200;
    for (int i = 0; i < objects.length; i++) {
      float x = objects[i].getPosition().x();
      float y = objects[i].getPosition().y();
      float dirX = objects[i].getDirection().x();
      float dirY = objects[i].getDirection().y();
      float radius = objects[i].getRadius();

      if (y <= topwall + radius) {
        objects[i].setDirection(new Direction(dirX, Math.abs(dirY)));
        objects[i].setPosition(new Position(x, topwall + radius));
      }
      else if (y >= bottomwall - radius) {
        objects[i].setDirection(new Direction(dirX, Math.abs(dirY) * -1));
        objects[i].setPosition(new Position(x, bottomwall - radius));
      }

      if (x <= leftwall + radius && !((y < 450 + goalsize / 2 && y > 450 - goalsize / 2) && objects[i].getClass() == Puck.class)) {
        objects[i].setDirection(new Direction(Math.abs(dirX), dirY));
        objects[i].setPosition(new Position(leftwall + radius, y));
      }
      else if (x >= rightwall - radius && !((y < 450 + goalsize / 2 && y > 450 - goalsize / 2) && objects[i].getClass() == Puck.class)) {
        objects[i].setDirection(new Direction(Math.abs(dirX) * -1, dirY));
        objects[i].setPosition(new Position(rightwall - radius, y));
      }

      if (x >= middlewall - radius && objects[i].equals(getPlayer2())) {
        objects[i].setDirection(new Direction(Math.abs(dirX) * -1, dirY));
        objects[i].setPosition(new Position(middlewall - radius, y));
      }
      else if (x <= middlewall + radius && objects[i].equals(getPlayer1())){
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

  private void friction() {
    for (int i = 0; i < objs.length; i++) {
      objs[i].setSpeed(objs[i].getSpeed() * Friktionskoefficient);
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
