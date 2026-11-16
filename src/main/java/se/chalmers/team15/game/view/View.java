package se.chalmers.team15.game.view;

import static com.raylib.Colors.*;
import static com.raylib.Raylib.*;

import se.chalmers.team15.game.model.Model;
import se.chalmers.team15.game.model.Model.Position;
import se.chalmers.team15.game.model.Model.Size;

public class View {
  private final Model model;

  private final RinkView rinkView = new RinkView();

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
     *
     *
     */



  }

  private void drawMap() {
    BeginDrawing();
    ClearBackground(BLACK);
    rinkView.drawRink(model.getRink());
      Position p = model.getPosition();
      DrawCircle((int) p.x(), (int) p.y(), 50, MAROON);
      DrawCircle((int) p.x(), (int) p.y(), 20, RED);
      System.out.println(p.x() + ", " + p.y());

      //the code below was for the original square striker
    /*Position position = model.getPosition();
    Size size = model.getSize();
    DrawRectangle((int) position.x(), (int) position.y(), size.width(), size.height(), MAROON);*/
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
