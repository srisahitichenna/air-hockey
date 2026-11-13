package se.chalmers.team15.game.view;

import static com.raylib.Colors.*;
import static com.raylib.Raylib.*;

import se.chalmers.team15.game.model.Model;
import se.chalmers.team15.game.model.Model.Position;
import se.chalmers.team15.game.model.Model.Size;

public class View {
  private final Model model;

  public View(Model model) {
    this.model = model;
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

  private void drawMap() {
    BeginDrawing();
    ClearBackground(RAYWHITE);
    Position position = model.getPosition();
    Size size = model.getSize();
    DrawRectangle((int) position.x(), (int) position.y(), size.width(), size.height(), MAROON);
    EndDrawing();
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
