import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GamePanel extends JPanel{
    static final int SCREEN_HEIGHT = 600;
    static final int SCREEN_WIDTH = 600;
    static final int TILE_SIZE = 20;
    static final int UNITS = (SCREEN_HEIGHT * SCREEN_WIDTH) / TILE_SIZE;

    final int[] x = new int[UNITS];
    final int[] y = new int[UNITS];

    int body_length = 3;
    int food_eaten = 0;
    int foodX, foodY;

    char dir = 'r';
    boolean running = false;
    long lastMoveTime = System.currentTimeMillis();
    final int MOVE_DELAY_MS = 70;
    Random random;

    public GamePanel(Game game) {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new KeyH());
        this.setFocusTraversalKeysEnabled(false);
        this.setDoubleBuffered(true);
        this.requestFocusInWindow();
        random = new Random();
        this.requestFocusInWindow();  // Ensure the panel is focused to receive key events
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void update() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastMoveTime >= MOVE_DELAY_MS) {
            move();
            checkFood();
            checkCollision();
            lastMoveTime = currentTime;
        }
    }

    public void draw(Graphics g) {
        if (running) {
            // Draw food
            g.setColor(Color.red);
            g.fillOval(foodX, foodY, TILE_SIZE, TILE_SIZE);

            // Draw snake
            for (int i = 0; i < body_length; i++) {
                if (i == 0) {
                    g.setColor(Color.green);  // Head of snake
                    g.fillRect(x[i], y[i], TILE_SIZE, TILE_SIZE);
                } else {
                    g.setColor(new Color(45, 180, 0));
//                    g.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255))); // Random body color
                    g.fillRect(x[i], y[i], TILE_SIZE, TILE_SIZE);
                }
            }

            // Draw score
            g.setColor(Color.red);
            g.setFont(new Font("Ink Free", Font.BOLD, 30));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: " + food_eaten, (SCREEN_WIDTH - metrics.stringWidth("Score: ")) / 2, g.getFont().getSize());

        } else {
            gameOver(g);
        }
    }

    public void newFood() {
        foodX = random.nextInt(SCREEN_WIDTH / TILE_SIZE) * TILE_SIZE;
        foodY = random.nextInt(SCREEN_HEIGHT / TILE_SIZE) * TILE_SIZE;
    }

    public void move() {
        for (int i = body_length; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        switch (dir) {
            case 'u':
                y[0] = y[0] - TILE_SIZE;
                break;
            case 'd':
                y[0] = y[0] + TILE_SIZE;
                break;
            case 'l':
                x[0] = x[0] - TILE_SIZE;
                break;
            case 'r':
                x[0] = x[0] + TILE_SIZE;
                break;
        }
    }

    public void checkFood() {
        if (x[0] == foodX && y[0] == foodY) {
            body_length++;
            food_eaten++;
            newFood();
        }
    }

    public void checkCollision() {
        for (int i = body_length; i > 0; i--) {
            if (x[0] == x[i] && y[0] == y[i]) {
                running = false;
            }
        }

//        if (x[0] < 0 || x[0] >= SCREEN_WIDTH || y[0] < 0 || y[0] >= SCREEN_HEIGHT) {
//            running = false;
//        }

        // IF head collides with LEFT border
        // IF head collides with LEFT border
        if (x[0] < 0) {
            x[0] = SCREEN_WIDTH - TILE_SIZE;
        }

        // IF head collides with RIGHT border
        if (x[0] >= SCREEN_WIDTH) {
            x[0] = 0;
        }

        // IF head collides with UP border
        if (y[0] < 0) {
            y[0] = SCREEN_HEIGHT - TILE_SIZE;
        }

        // IF head collides with DOWN border
        if (y[0] >= SCREEN_HEIGHT) {
            y[0] = 0;
        }
    }

    public void gameOver(Graphics g) {
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Game Over", (SCREEN_WIDTH - metrics.stringWidth("Game Over")) / 2, SCREEN_HEIGHT / 2);

        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 30));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Score: " + food_eaten, (SCREEN_WIDTH - metrics2.stringWidth("Score: ")) / 2, g.getFont().getSize());
    }


    public class KeyH extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (dir != 'r') dir = 'l';
                    break;
                case KeyEvent.VK_RIGHT:
                    if (dir != 'l') dir = 'r';
                    break;
                case KeyEvent.VK_UP:
                    if (dir != 'd') dir = 'u';
                    break;
                case KeyEvent.VK_DOWN:
                    if (dir != 'u') dir = 'd';
                    break;
            }
        }
    }
}



