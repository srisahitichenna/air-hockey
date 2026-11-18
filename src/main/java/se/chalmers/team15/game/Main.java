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
    Model model2 =
        new Model(new Model.Position(1400, 415), new Model.Velocity(0, 0), new Model.Size(80, 80));
    View view = new View(model, model2);

    Controller controller = new Controller(model, KEY_LEFT, KEY_RIGHT, KEY_UP, KEY_DOWN);
    Controller controller2 = new Controller(model2, KEY_A, KEY_D, KEY_W, KEY_S);

    model.startMatch();

    while (!WindowShouldClose()) {
      float dt = GetFrameTime();
      controller.update();
      controller2.update();
      BeginDrawing();
      ClearBackground(BLACK);

      model.update(dt);
      model2.update(dt);
      view.update();
      EndDrawing();
    }

    CloseWindow();
  }
}
