package se.chalmers.team15.game.model;

public class Rink {
    private final float width;
    private final float height;
    private final float cornerRadius;

    public Rink(float width, float height, float cornerRadius) {
        this.width = width;
        this.height = height;
        this.cornerRadius = cornerRadius;
    }

    public float getWidth() {
        return width; }

    public float getHeight() {
        return height; }

    public float getCornerRadius() {
        return cornerRadius; }
}
