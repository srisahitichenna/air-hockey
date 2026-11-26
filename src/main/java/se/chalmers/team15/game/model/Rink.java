package se.chalmers.team15.game.model;

public class Rink {
  private final float x;
  private final float y;
  private final float width;
  private final float height;
  private final float cornerRadius;
  private static final float GOAL_HALF_HEIGHT = 100f;

  public enum GoalSide {
    NONE,
    LEFT,
    RIGHT
  }

  public Rink(float x, float y, float width, float height, float cornerRadius) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.cornerRadius = cornerRadius;
  }


  public float getWidth() {
    return width;
  }

  public float getHeight() {
    return height;
  }

  public float getCornerRadius() {
    return cornerRadius;
  }

  public float getGoalHalfHeight() {
    return GOAL_HALF_HEIGHT;
  }

  public float getLeft() {
    return x;
  }

  public float getRight() {
    return x + width;
  }

  public float getTop() {
    return y;
  }

  public float getBottom() {
    return y + height;
  }

  public float getCenterX() {
    return x + width / 2f;
  }

  public float getCenterY() {
    return y + height / 2f;
  }

  public GoalSide checkGoal(Position puck) {
    if (Math.abs(puck.y() - getCenterY()) >= GOAL_HALF_HEIGHT) return GoalSide.NONE;

    if (puck.x() <= getLeft()) return GoalSide.LEFT;
    if (puck.x() >= getRight()) return GoalSide.RIGHT;
    return GoalSide.NONE;
  }
}
