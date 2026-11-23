package se.chalmers.team15.game.model;

public class Rink {
  private final float width;
  private final float height;
  private final float cornerRadius;
  private static final float goalRadius =  100f;
  public enum goalSide {none, left, right}

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

  //public goalSide checkGoal(Model.Position )

  public float getGoalRadius(){ return goalRadius; }




}



