package se.chalmers.team15.game.model;

public record Position(float x, float y) {
    /** In pixels, measured from the top-left corner of the window. */
    public Position moveBy(Direction velocity, float dt) {
      return new Position(x + velocity.x() * dt, y + velocity.y() * dt);
    }
}
