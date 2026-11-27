package se.chalmers.team15.game.view;

import static com.raylib.Colors.*;
import static com.raylib.Raylib.*;

import com.raylib.Raylib;
import se.chalmers.team15.game.model.Rink;

public class RinkView {

  public float getOffsetX(Rink rink) {
    return (GetScreenWidth() - rink.getWidth()) / 2f;
  }

  public float getOffsetY(Rink rink) {
    return (GetScreenHeight() - rink.getHeight()) / 2f;
  }

  public void drawRink(Rink rink) {
    float offsetX = getOffsetX(rink);
    float offsetY = getOffsetY(rink);

    float roundness = rink.getCornerRadius() / (Math.min(rink.getWidth(), rink.getHeight()) / 2f);

    Raylib.Rectangle rec =
        new Raylib.Rectangle()
            .x(offsetX)
            .y(offsetY)
            .width(rink.getWidth())
            .height(rink.getHeight());

    DrawRectangleRounded(rec, roundness, 16, RAYWHITE);
    DrawRectangleRoundedLines(rec, roundness, 16, BLACK);

    int midX = (int) (offsetX + rink.getWidth() / 2f);
    int top = (int) offsetY;
    int bottom = (int) (offsetY + rink.getHeight());

    DrawCircle(midX, (int) (offsetY + rink.getHeight() / 2f), 62, BLUE);
    DrawCircle(midX, (int) (offsetY + rink.getHeight() / 2f), 60, RAYWHITE);
    DrawCircle(midX, (int) (offsetY + rink.getHeight() / 2f), 4, RED);

    DrawRectangle(midX - 1, top, 2, bottom - top, RED);

    DrawRectangle((midX / 2) - 1, top, 2, bottom - top, BLUE);
    DrawRectangle((3 * midX / 2) - 1, top, 2, bottom - top, BLUE);

    DrawCircleLines(
        (GetScreenWidth() - (int) rink.getWidth()) / 2,
        (top + bottom) / 2,
        rink.getGoalHalfHeight(),
        RED);
    DrawCircleLines(
        (int) rink.getWidth() + (GetScreenWidth() - (int) rink.getWidth()) / 2,
        (top + bottom) / 2,
        rink.getGoalHalfHeight(),
        RED);
  }
}
