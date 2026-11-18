package se.chalmers.team15.game.view;

import static com.raylib.Colors.*;
import static com.raylib.Raylib.*;

import se.chalmers.team15.game.model.Model;
import se.chalmers.team15.game.model.Model.Position;
import se.chalmers.team15.game.model.Puck;

public class View {
  private final Model model;
  private final Model model2;

  private final RinkView rinkView = new RinkView();

  public View(Model model, Model model2) {
  private final ScoreView scoreView = new ScoreView(rinkView);
  private final TimerView timerView = new TimerView(rinkView);

  public View(Model model) {
    this.model = model;
    this.model2 = model2;
  }

  public void update() {
    BeginDrawing();
    drawMap();
    drawPuck();
    drawScore();
    drawTimer();
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

  public void drawMap() {

    rinkView.drawRink(model.getRink());
    Position position = model.getPosition();
    Position position2 = model2.getPosition();
    Size size = model.getSize();
    Size size2 = model2.getSize();
    DrawRectangle((int) position.x(), (int) position.y(), size.width(), size.height(), MAROON);
    DrawRectangle((int) position2.x(), (int) position2.y(), size2.width(), size2.height(), BLUE);
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

    DrawCircle((int) position.x(), (int) position.y(), (int) radius, BLACK);
    DrawCircle((int) position.x(), (int) position.y(), (int) 40, DARKGRAY);
  }

  public void drawScore() {
    scoreView.drawScore(model);
  }

  public void drawTimer() {
    timerView.drawTimer(model);
  }
}
