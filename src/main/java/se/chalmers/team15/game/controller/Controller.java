package se.chalmers.team15.game.controller;

import static com.raylib.Raylib.*;

import se.chalmers.team15.game.model.Model;

public class Controller {
  /** Speed while an arrow key is held, in pixels per second. */
  private static final float SPEED = 20f;

  private final Model model;
  private final int strikerNr;
  private final int KEY_LEFT;
  private final int KEY_RIGHT;
  private final int KEY_UP;
  private final int KEY_DOWN;

  /*
   * private enum Key {
   * LEFT(KEY_LEFT, -1, 0),
   * RIGHT(KEY_RIGHT, 1, 0),
   * UP(KEY_UP, 0, -1),
   * DOWN(KEY_DOWN, 0, 1);
   * 
   * private final int raylibCode;
   * private final float xVel;
   * private final float yVel;
   * 
   * Key(int raylibCode, int xVel, int yVel) {
   * this.raylibCode = raylibCode;
   * this.xVel = xVel * SPEED;
   * this.yVel = yVel * SPEED;
   * }
   * }
   */

  public Controller(Model model, int strikerNr, int KEY_LEFT, int KEY_RIGHT, int KEY_UP, int KEY_DOWN) {
    this.strikerNr = strikerNr;
    this.model = model;
    this.KEY_LEFT = KEY_LEFT;
    this.KEY_RIGHT = KEY_RIGHT;
    this.KEY_UP = KEY_UP;
    this.KEY_DOWN = KEY_DOWN;
  }

  public void update() {
    processInput();
  }

  private void processInput() {
    float xVelocity = 0;
    float yVelocity = 0;

    if (IsKeyDown(KEY_LEFT)) {
      xVelocity -= SPEED;
    }
    if (IsKeyDown(KEY_RIGHT)) {
      xVelocity += SPEED;
    }
    if (IsKeyDown(KEY_UP)) {
      yVelocity -= SPEED;
    }
    if (IsKeyDown(KEY_DOWN)) {
      yVelocity += SPEED;
    }

    model.setInput(xVelocity, yVelocity, strikerNr);
  }
}
