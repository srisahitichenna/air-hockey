package se.chalmers.team15.game;

import static com.raylib.Colors.*;
import static com.raylib.Raylib.*;

import se.chalmers.team15.game.controller.Controller;
import se.chalmers.team15.game.model.Model;
import se.chalmers.team15.game.view.View;

public class Main {
  private static final int SCREEN_WIDTH = 1800;
  private static final int SCREEN_HEIGHT = 900;
  private static final int FRAMES_PER_SECOND = 60;
  private static final String TITLE = "Game";

  public static void main(String[] args) {
    InitWindow(SCREEN_WIDTH, SCREEN_HEIGHT, TITLE);
    SetTargetFPS(FRAMES_PER_SECOND);

    Model model = new Model();
    View view = new View(model);

    Controller controller =
        new Controller(model.getPlayer1(), KEY_LEFT, KEY_RIGHT, KEY_UP, KEY_DOWN);
    Controller controller2 = new Controller(model.getPlayer2(), KEY_A, KEY_D, KEY_W, KEY_S);

    model.startMatch();

    while (!WindowShouldClose()) {
      float dt = GetFrameTime();
      controller.update();
      controller2.update();
      ClearBackground(BLACK);

      model.update(dt);
      view.update();
    }

    CloseWindow();
  }
}
