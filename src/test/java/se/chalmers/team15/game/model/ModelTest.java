package se.chalmers.team15.game.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import se.chalmers.team15.game.model.Model.Direction;;

class ModelTest {
  private static final float TOLERANCE = 0.0001f;

/*   @Test
  void positionMovesWithVelocityAfterUpdate() {
    Model model = new Model(new Direction(10f, -20f));

    model.update(1000);
    model.update(0.5f);

    assertEquals(905f, model.getPuck().getPosition().x(), TOLERANCE);
    assertEquals(440f, model.getPuck().getPosition().y(), TOLERANCE);
  }

  @Test
  void puckPositionStaysWhenVelocityIsZero() {
    Model model = new Model(new Direction(10f, -20f));

    model.update(1000f);
    model.getPuck().setDirection(new Direction(0f, 0f));
    model.getPuck().setSpeed(0);
    model.update(0.5f);

    assertEquals(900f, model.getPuck().getPosition().x(), TOLERANCE);
    assertEquals(450f, model.getPuck().getPosition().y(), TOLERANCE);
  }
*/
}
