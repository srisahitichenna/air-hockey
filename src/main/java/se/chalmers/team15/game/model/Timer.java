package se.chalmers.team15.game.model;

public class Timer {
  private float start;
  private float remaining;
  private boolean running = false;

  public Timer(int start) {
    this.start = start;
    this.remaining = start;
  }

  public void startTimer() {
    running = true;
  }

  public void endTimer() {
    running = false;
  }

  public void resetTimer() {
    remaining = start;
    running = false;
  }

  public void update(float dt) {
    if (!running) return;
    remaining -= dt;
    if (remaining <= 0) {
      remaining = 0;
      running = false;
    }
  }

  public float getRemaining() {
    return remaining;
  }

  public boolean isFinished() {
    return remaining <= 0;
  }
}
