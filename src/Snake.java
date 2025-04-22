import java.awt.*;

public class Snake {
    final int[] x = new int[Game_panel.UNITS];
    final int[] y = new int[Game_panel.UNITS];
    int body_length = 3;
    char dir = 'r';
    boolean running = false;
    long lastMoveTime = System.currentTimeMillis();
    final int MOVE_DELAY_MS = 85;
    Food food;

    public Snake() {

    }

    public void setFood(Food food) {
        this.food = food;
    }

    public void update() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastMoveTime >= MOVE_DELAY_MS) {
            move();
            food.checkFood();
            checkCollision();
            lastMoveTime = currentTime;
        }
    }

    public void render(Graphics g){
        for (int i = 0; i < body_length; i++) {
            if (i == 0) {
                g.setColor(Color.green);  // Head of snake
                g.fillRect(x[i], y[i], Game_panel.TILE_SIZE, Game_panel.TILE_SIZE);
            } else {
                g.setColor(new Color(45, 180, 0));
//                    g.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255))); // Random body color
                g.fillRect(x[i], y[i], Game_panel.TILE_SIZE, Game_panel.TILE_SIZE);
            }
        }
    }

    public void move() {
        for (int i = body_length; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        switch (dir) {
            case 'u':
                y[0] = y[0] - Game_panel.TILE_SIZE;
                break;
            case 'd':
                y[0] = y[0] + Game_panel.TILE_SIZE;
                break;
            case 'l':
                x[0] = x[0] - Game_panel.TILE_SIZE;
                break;
            case 'r':
                x[0] = x[0] + Game_panel.TILE_SIZE;
                break;
        }
    }


    public void checkCollision() {
        for (int i = body_length; i > 0; i--) {
            if (x[0] == x[i] && y[0] == y[i]) {
                running = false;
                break;
            }
        }

//        if (x[0] < 0 || x[0] >= SCREEN_WIDTH || y[0] < 0 || y[0] >= SCREEN_HEIGHT) {
//            running = false;
//        }

        // IF head collides with LEFT border
        if (x[0] < 0) {
            x[0] = Game_panel.SCREEN_WIDTH - Game_panel.TILE_SIZE;
        }

        // IF head collides with RIGHT border
        if (x[0] >= Game_panel.SCREEN_WIDTH) {
            x[0] = 0;
        }

        // IF head collides with UP border
        if (y[0] < 0) {
            y[0] = Game_panel.SCREEN_HEIGHT - Game_panel.TILE_SIZE;
        }

        // IF head collides with DOWN border
        if (y[0] >= Game_panel.SCREEN_HEIGHT) {
            y[0] = 0;
        }
    }

}
