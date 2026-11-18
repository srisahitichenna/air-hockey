package se.chalmers.team15.game.view;

import static com.raylib.Colors.*;
import static com.raylib.Raylib.*;

import se.chalmers.team15.game.model.Model;
import se.chalmers.team15.game.model.Model.Position;
import se.chalmers.team15.game.model.Puck;

public class View {
  private final Model model;

  private final RinkView rinkView = new RinkView();
  private final ScoreView scoreView = new ScoreView(rinkView);
  private final TimerView timerView = new TimerView(rinkView);

  public View(Model model) {
    this.model = model;
  }

  public void update() {
    BeginDrawing();
    drawMap();
    drawStrikers();
    drawPuck();
    drawScore();
    drawTimer();
    EndDrawing();
  }

  public void drawMap() {
    rinkView.drawRink(model.getRink());
  }

  private void drawStrikers() {
    Position position1 = model.getPlayer1().getPosition();
    Position position2 = model.getPlayer2().getPosition();
    DrawCircle((int) position1.x(), (int) position1.y(), 50, MAROON);
    DrawCircle((int) position1.x(), (int) position1.y(), 20, RED);
    DrawCircle((int) position2.x(), (int) position2.y(), 50, DARKBLUE);
    DrawCircle((int) position2.x(), (int) position2.y(), 20, BLUE);
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
