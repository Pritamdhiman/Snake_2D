import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class Game_panel extends JPanel{
    static final int SCREEN_HEIGHT = 600;
    static final int SCREEN_WIDTH = 800;
    static final int TILE_SIZE = 20;
    static final int UNITS = (SCREEN_HEIGHT * SCREEN_WIDTH) / TILE_SIZE;
    private final Game game;

    public Game_panel(Game game) {
        this.game = game;
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.setFocusTraversalKeysEnabled(false);
        this.setDoubleBuffered(true);

    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
        game.render(g);
    }

    public void draw(Graphics g) {
        // Grids
        g.setColor(Color.darkGray);
        for (int x = 0; x <= getWidth(); x += TILE_SIZE) {
            g.drawLine(x, 0, x, getHeight());
        }
        for (int y = 0; y <= getHeight(); y += TILE_SIZE) {
            g.drawLine(0, y, getWidth(), y);
        }
    }


}



