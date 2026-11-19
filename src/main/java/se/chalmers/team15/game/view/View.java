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
  private final WinnerView winnerView = new WinnerView();

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
    if (model.getTimer().isFinished()) {
      drawWinner();
    }
    EndDrawing();
  }

  public void drawMap() {
    rinkView.drawRink(model.getRink());
  }

  private void drawStrikers() {
    Position position1 = model.getPlayer1().getPosition();
    Position position2 = model.getPlayer2().getPosition();
    float radius1 = model.getPlayer1().getRadius();
    float radius2 = model.getPlayer2().getRadius();
    DrawCircle(Math.round(position1.x()), Math.round(position1.y()), Math.round(radius1), MAROON);
    DrawCircle(
        Math.round(position1.x()), Math.round(position1.y()), Math.round(radius1 * 0.4), RED);
    DrawCircle(Math.round(position2.x()), Math.round(position2.y()), Math.round(radius2), DARKBLUE);
    DrawCircle(
        Math.round(position2.x()), Math.round(position2.y()), Math.round(radius2 * 0.4), BLUE);
  }

  public void drawPuck() {
    Puck puck = model.getPuck();
    Position position = puck.getPosition();
    float radius = puck.getRadius();

    DrawCircle(Math.round(position.x()), Math.round(position.y()), Math.round(radius), LIME);
    DrawCircle(Math.round(position.x()), Math.round(position.y()), Math.round(radius * 0.8), GREEN);
  }

  public void drawScore() {
    scoreView.drawScore(model);
  }

  public void drawTimer() {
    timerView.drawTimer(model);
  }

  public void drawWinner() {
    winnerView.drawWinner(model);
  }
}
