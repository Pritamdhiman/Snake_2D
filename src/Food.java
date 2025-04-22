import java.awt.*;
import java.util.Random;

public class Food {
    int food_eaten = 0;
    int foodX, foodY;
    Random random;
    Snake s;

    public Food(Snake s) {
        this.s = s;
        random = new Random();
    }

    public void render(Graphics g){
        g.setColor(Color.red);
        g.fillOval(foodX, foodY, Game_panel.TILE_SIZE, Game_panel.TILE_SIZE);
    }

    public void newFood() {
        foodX = random.nextInt(Game_panel.SCREEN_WIDTH / Game_panel.TILE_SIZE) * Game_panel.TILE_SIZE;
        foodY = random.nextInt(Game_panel.SCREEN_HEIGHT / Game_panel.TILE_SIZE) * Game_panel.TILE_SIZE;
    }

    public void checkFood() {
        if (s.x[0] == foodX && s.y[0] == foodY) {
            s.body_length++;
            food_eaten++;
            newFood();
        }
    }
}
