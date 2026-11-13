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
    draw();
  }

  private void draw() {
    BeginDrawing();
    ClearBackground(BLACK);
    rinkView.drawRink(model.getRink());
    Position position = model.getPosition();
    Size size = model.getSize();
    DrawRectangle((int) position.x(), (int) position.y(), size.width(), size.height(), MAROON);
    EndDrawing();
  }
}
