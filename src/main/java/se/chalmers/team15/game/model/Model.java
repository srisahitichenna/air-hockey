package se.chalmers.team15.game.model;

public class Model {

  /** In pixels, measured from the top-left corner of the window. */
  public record Position(float x, float y) {
    public Position moveBy(Velocity velocity, float dt) {
      return new Position(x + velocity.x() * dt, y + velocity.y() * dt);
    }
  }

  private int MATCH_LENGTH = 300; // 5 min match length

  private final Rink rink = new Rink(1600, 800, 80);
  private final Puck puck = new Puck(900, 450);
  private final Score score = new Score();
  private final Timer timer = new Timer(MATCH_LENGTH);

  private final Striker player1 = new Striker(new Position(1400, 415), new Size(80, 80));
  private final Striker player2 = new Striker(new Position(200, 415), new Size(80, 80));

  public Rink getRink() {
    return rink;
  }

  public Puck getPuck() {
    return puck;
  }

  /** In pixels per second. */
  public record Velocity(float x, float y) {}

  public Score getScore() {
    return score;
  }

  public Striker getPlayer1() {
    return player1;
  }

  public Striker getPlayer2() {
    return player2;
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

  /** In pixels. */
  public record Size(int width, int height) {}

  /*   private Position position;
  private Velocity velocity;
  private Velocity speed = new Velocity(0, 0); */

  /* public Model() {
     this(new Position(200, 415), new Velocity(0, 0), new Size(80, 80));
     // this(new Position(1350, 450), new Velocity(0, 0), new Size(80, 80));
   }

   public Model(Position position, Velocity velocity) {
     this.position = position;
     this.velocity = velocity;
   }
  */
  public void startMatch() {
    score.resetScore();
    // reset puck
    // reset strikers
    timer.resetTimer();
    timer.startTimer();
  }

  /** Advances the simulation by {@code dt} seconds. */
  public void update(float dt) {
    timer.update(dt);
    if (timer.isFinished()) {
      // stop puck and players
      // announce winner
      return;
    }
    player1.update(dt);
    player2.update(dt);
  }
}
