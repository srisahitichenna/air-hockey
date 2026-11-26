package se.chalmers.team15.game.model;

import static com.raylib.Colors.BLACK;
import static com.raylib.Colors.DARKBLUE;
import static com.raylib.Raylib.GetMousePosition;
import static com.raylib.Raylib.DrawRectangle;
import static com.raylib.Raylib.DrawText;
import static com.raylib.Raylib.GetMousePosition;
import static com.raylib.Colors.BLACK;
import static com.raylib.Colors.DARKBLUE;
import static com.raylib.Colors.WHITE;
import java.util.Vector;

import com.raylib.Raylib;
import com.raylib.Raylib.Vector2;

public class Button {
    private int x;
    private int y;
    private int width;
    private int height;
    private String text;

    public Button(int x, int y, int width, int height, String text) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.text = text;
    }

    public boolean isHovered() {
        Vector2 mouse = GetMousePosition();
        return ((mouse.x() >= x && mouse.x() <= x + width) && (mouse.y() >= y && mouse.y() <= y + height));
    }

    public boolean isClicked() {
        return (isHovered() && Raylib.IsMouseButtonPressed(Raylib.MOUSE_BUTTON_LEFT));
    }

    public void draw() {
        /* Color changing when hovering button */
        if (isHovered()) {
            DrawRectangle(x, y, width, height, DARKBLUE);
        } else {
            DrawRectangle(x, y, width, height, BLACK);
        }

        DrawText(text, x + 20, y + 20, 20, WHITE);
    }
}
