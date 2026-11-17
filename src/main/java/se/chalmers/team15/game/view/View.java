package se.chalmers.team15.game.view;

import static com.raylib.Colors.*;
import static com.raylib.Raylib.*;

import se.chalmers.team15.game.model.Model;
import se.chalmers.team15.game.model.Model.Position;
import se.chalmers.team15.game.model.Model.Size;

public class View {
  private final Model model;
  private final Model model2;

  private final RinkView rinkView = new RinkView();

  public View(Model model, Model model2) {
    this.model = model;
    this.model2 = model2;
  }

  public void update() {
    drawMap();
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
  }

  public void drawPuck() {
    BeginDrawing();
    /*
     * Not done
     */
    EndDrawing();
  }

  public void drawScore() {
    BeginDrawing();
    /*
     * Not done
     */
    EndDrawing();
  }
}
