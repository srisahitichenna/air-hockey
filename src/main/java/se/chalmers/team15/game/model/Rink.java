package se.chalmers.team15.game.model;

import se.chalmers.team15.game.view.RinkView;

import static com.raylib.Raylib.GetScreenWidth;

public class Rink {
  private final float width;
  private final float height;
  private final float cornerRadius;
  private static final float goalRadius =  100f;
  public enum GoalSide {NONE, LEFT, RIGHT}

  public Rink(float width, float height, float cornerRadius) {
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

  public float getGoalRadius(){ return goalRadius; }

  public GoalSide checkGoal(Model.Position puck){
      float centerY = getHeight()/2f;
      boolean inGoalRange = Math.abs(puck.y() - centerY) < getGoalRadius();
      if (!inGoalRange) return GoalSide.NONE;

      if (puck.x() <=  (GetScreenWidth() - getWidth())/2) return GoalSide.LEFT;
      if (puck.x() >= getWidth() + (GetScreenWidth() -getWidth())/2) return GoalSide.RIGHT;
      return GoalSide.NONE;
  }


}



