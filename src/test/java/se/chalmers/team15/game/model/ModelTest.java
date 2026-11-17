package se.chalmers.team15.game.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import se.chalmers.team15.game.model.Model.Position;
import se.chalmers.team15.game.model.Model.Size;
import se.chalmers.team15.game.model.Model.Direction;;

class ModelTest {
  private static final float TOLERANCE = 0.0001f;

  @Test
  void positionMovesWithVelocityAfterUpdate() {
    Model model = new Model(new Position(100f, 200f), new Direction(10f, -20f), new Size(50, 50));

    model.update(0.5f);

    assertEquals(105f, model.getPosition().x(), TOLERANCE);
    assertEquals(190f, model.getPosition().y(), TOLERANCE);
  }

  @Test
  void positionStaysWhenVelocityIsZero() {
    Model model = new Model(new Position(100f, 200f), new Direction(10f, -20f), new Size(50, 50));

    model.setDirection(new Direction(0f, 0f));
    model.update(0.5f);

    assertEquals(100f, model.getPosition().x(), TOLERANCE);
    assertEquals(200f, model.getPosition().y(), TOLERANCE);
  }
}
