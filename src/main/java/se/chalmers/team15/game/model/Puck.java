package se.chalmers.team15.game.model;

/*hi  */
public class Puck {
  // Variables
  private Model.Position position;
  private Model.Velocity velocity;

  private static final float RADIUS = 50;

  // Making the puck
  public Puck(float xPos, float yPos) {
    this.position = new Model.Position(xPos, yPos);
    this.velocity = new Model.Velocity(0, 0);

    /*
     * if (position.y() == 100 || position.y() == 1000) {
     * this.velocity = new Model.Velocity(this.velocity.x(), this.velocity.y() *
     * -1);
     * }
     *
     * if (position.x() == 100 || position.x() == 2000) {
     * this.velocity = new Model.Velocity(this.velocity.x() * -1,
     * this.velocity.y());
     * }
     */
  }

  // Getters
  public Model.Position getPosition() {
    return position;
  }

  public Model.Velocity getVelocity() {
    return velocity;
  }

  public float getRadius() {
    return RADIUS;
  }
}
