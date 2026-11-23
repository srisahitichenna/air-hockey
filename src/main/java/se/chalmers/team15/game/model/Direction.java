package se.chalmers.team15.game.model;

public class Direction {
    private float x;
    private float y;

    public Direction(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getMagnitudeAndNormalize() {
        if (this.x == 0 && this.y == 0) {
            return 0;
        }
        float magnitude = (float) Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2));
        this.x = x / magnitude;
        this.y = y / magnitude;
        return magnitude;
    }

    public float x() {
        return this.x;
    }

    public float y() {
        return this.y;
    }
}
