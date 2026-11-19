package se.chalmers.team15.game.view;

import static com.raylib.Colors.BLACK;
import static com.raylib.Colors.WHITE;
import static com.raylib.Raylib.DrawRectangle;
import static com.raylib.Raylib.DrawText;
import static com.raylib.Raylib.Fade;
import static com.raylib.Raylib.GetScreenHeight;
import static com.raylib.Raylib.GetScreenWidth;
import static com.raylib.Raylib.MeasureText;

import se.chalmers.team15.game.model.Model;

public class WinnerView {

  public void drawWinner(Model model) {
    String text =
        switch (model.getWinner()) {
          case PLAYER1 -> "PLAYER 1 WINS!";
          case PLAYER2 -> "PLAYER 2 WINS!";
          case DRAW -> "IT'S A DRAW!";
        };

    int fontsize = 120;
    int x = (GetScreenWidth() - MeasureText(text, fontsize)) / 2;
    int y = GetScreenHeight() / 2 - fontsize / 2;

    DrawRectangle(0, 0, GetScreenWidth(), GetScreenHeight(), Fade(BLACK, 0.6f));
    DrawText(text, x, y, fontsize, WHITE);
  }
}
