package se.chalmers.team15.game;

import static com.raylib.Raylib.*;

import se.chalmers.team15.game.controller.Controller;
import se.chalmers.team15.game.model.Model;
import se.chalmers.team15.game.view.View;

public class Main {
  private static final int SCREEN_WIDTH = 1800;
  private static final int SCREEN_HEIGHT = 900;
  private static final int FRAMES_PER_SECOND = 60;
  private static final String TITLE = "Game";
676767676767676767676767667
  public static void main(String[] args) {
    InitWindow(SCREEN_WIDTH, SCREEN_HEIGHT, TITLE);
    SetTargetFPS(FRAMES_PER_SECOND);

    Model model = new Model();
    View view = new View(model);
    Controller controller = new Controller(model);

    while (!WindowShouldClose()) {
      model.update(GetFrameTime());
      view.update();
      controller.update();
    }

    CloseWindow();
  }
}
