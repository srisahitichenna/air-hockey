package se.chalmers.team15.game.view;

import static com.raylib.Colors.*;
import static com.raylib.Raylib.*;

import se.chalmers.team15.game.model.Model;
import se.chalmers.team15.game.model.Rink;

public class ScoreView {

  private final RinkView rinkView;

  public ScoreView(RinkView rinkView) {
    this.rinkView = rinkView;
  }

  public void drawScore(Model model) {
    Rink rink = model.getRink();
    float offsetX = rinkView.getOffsetX(rink);
    float offsetY = rinkView.getOffsetY(rink);

    String text = model.getScore().getPlayer1Score() + " - " + model.getScore().getPlayer2Score();
    int fontSize = 50;
    int textWidth = MeasureText(text, fontSize);

    int x = (int) (offsetX + rink.getWidth() / 2f) - textWidth / 2;
    int y = (int) offsetY - 48;

    DrawText(text, x, y, fontSize, GREEN);
  }
}
