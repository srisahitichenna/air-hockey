package se.chalmers.team15.game.view;

import static com.raylib.Colors.*;
import static com.raylib.Raylib.*;

import se.chalmers.team15.game.model.Model;
import se.chalmers.team15.game.model.Model.Position;
import se.chalmers.team15.game.model.Model.Size;
import se.chalmers.team15.game.model.Puck;

public class View {
  private final Model model;

  private final RinkView rinkView = new RinkView();

  private final ScoreView scoreView = new ScoreView(rinkView);

  public View(Model model) {
    this.model = model;
  }

  public void update() {
    BeginDrawing();
    drawMap();
    drawPuck();
    EndDrawing();
  }

  public void drawPlayers() {
    /*
     * BeginDrawing();
     * Position position = model.getPosition();
     * DrawCircle((int) position.x(), (int) position.y(), 1, MAROON);
     * EndDrawing();
     *
     * This is a bad code, we need to implement this better
     * i just tried to experiment - Charlie
     *
     *
     */

  }

  private void drawMap() {
    ClearBackground(BLACK);
    rinkView.drawRink(model.getRink());
    Position p = model.getPosition();
    DrawCircle((int) p.x(), (int) p.y(), 50, MAROON);
    DrawCircle((int) p.x(), (int) p.y(), 20, RED);
    System.out.println(p.x() + ", " + p.y());
  }

  public void drawPuck() {
    Puck puck = model.getPuck();
    Position position = puck.getPosition();
    float radius = puck.getRadius();

    DrawCircle(
        (int) position.x(),
        (int) position.y(),
        (int) radius,
        BLACK);
    DrawCircle(
        (int) position.x(),
        (int) position.y(),
        (int) 40,
        DARKGRAY);
  }

  public void drawScore() {
    scoreView.drawScore(model);;
  }
}
