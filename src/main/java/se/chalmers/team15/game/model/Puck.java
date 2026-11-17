package se.chalmers.team15.game.model;

/*hi  */
public class Puck {
    // Variables
    private Model.Position position;
    private Model.Direction direction;

    private static final float RADIUS = 50;

    // Making the puck
    public Puck(float xPos, float yPos) {
        this.position = new Model.Position(xPos, yPos);
        this.direction = new Model.Direction(0, 0);
    }

    // Getters
    public Model.Position getPosition() {
        return position;
    }

    public Model.Direction getDirection() {
        return direction;
    }

    public float getRadius() {
        return RADIUS;
    }
}