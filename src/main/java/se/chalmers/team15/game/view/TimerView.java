package se.chalmers.team15.game.view;

import static com.raylib.Colors.GREEN;
import static com.raylib.Raylib.DrawText;
import static com.raylib.Raylib.GetScreenHeight;
import static com.raylib.Raylib.MeasureText;

import se.chalmers.team15.game.model.Model;
import se.chalmers.team15.game.model.Rink;
import se.chalmers.team15.game.model.Timer;

public class TimerView {

  private final RinkView rinkView;

  public TimerView(RinkView rinkView) {
    this.rinkView = rinkView;
  }

  public void drawTimer(Model model) {
    Rink rink = model.getRink();
    Timer timer = model.getTimer();

    float offsetX = rinkView.getOffsetX(rink);

    int total = (int) Math.ceil(timer.getRemaining());
    String text = String.format("%d:%02d", total / 60, total % 60);

    int fontSize = 40;
    int textWidth = MeasureText(text, fontSize);
    int x = (int) (offsetX + rink.getWidth() / 2f) - textWidth / 2;
    int y = (int) GetScreenHeight() - 48;

    DrawText(text, x, y, fontSize, GREEN);
  }
}
