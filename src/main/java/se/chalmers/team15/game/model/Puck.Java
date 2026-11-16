package se.chalmers.team15.game.model;

public class Puck {

    // Variables
    private Model.Position position;
    private Model.Velocity velocity;

    private static final float RADIUS = 50;

    // Making the puck
    public Puck(float xPos, float yPos) {
        this.position = new Model.Position(xPos, yPos);
        this.velocity = new Model.Velocity(0, 0);
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