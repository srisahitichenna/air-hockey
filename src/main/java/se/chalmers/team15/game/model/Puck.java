package se.chalmers.team15.game.model;

/*hi  */
public class Puck {
    // Variables
    private Model.Position position;
    private Model.Direction direction;
    private float speed = 0;

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
    
    public void setPosition(Model.Position position) {
        this.position = position;
    }

    public Model.Direction getDirection() {
        return direction;
    }

    public void setDirection(float x, float y) {
        this.direction = new Model.Direction(x, y);
    }

    public float getRadius() {
        return RADIUS;
    }
}