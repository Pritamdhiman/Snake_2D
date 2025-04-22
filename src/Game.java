import java.awt.*;

public class Game implements Runnable {
    private Game_panel gp;
    private Game_frame gf;
    private Thread game_thread;
    final static int FPS = 90;
    final static int UPS = 90;
    private Snake s;
    private Food food;

    public Game(){
        init_classes();
        gp.requestFocusInWindow();
        start_game_loop();
    }

    private void init_classes(){
        gp = new Game_panel(this);
        gf = new Game_frame(gp);
//        gp.requestFocus();
        s = new Snake();
        food = new Food(s);
        gp.addKeyListener(new KeyH(s));
        s.setFood(food);
    }

    private void start_game_loop(){
        food.newFood();
        s.running = true;
        game_thread = new Thread(this);
        game_thread.start();
    }

    public void update(){
        // Update everything like, game_panel, MC, NPC, etc.
        s.update();
    }

    public void render(Graphics g){
        // Render everything like, game_panel, MC, NPC, etc.
        if (s.running) {
            food.render(g);
            s.render(g);
            g.setColor(Color.red);
            g.setFont(new Font("Ink Free", Font.BOLD, 30));
            FontMetrics metrics = g.getFontMetrics(g.getFont());
            g.drawString("Score: " + food.food_eaten, (gp.SCREEN_WIDTH - metrics.stringWidth("Score: ")) / 2, g.getFont().getSize());

        } else {
            gameOver(g);
        }

    }

    public void gameOver(Graphics g) {
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        FontMetrics metrics = g.getFontMetrics(g.getFont());
        g.drawString("Game Over", (gp.SCREEN_WIDTH - metrics.stringWidth("Game Over")) / 2, gp.SCREEN_HEIGHT / 2);

        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 30));
        FontMetrics metrics2 = g.getFontMetrics(g.getFont());
        g.drawString("Score: " + food.food_eaten, (gp.SCREEN_WIDTH - metrics2.stringWidth("Score: ")) / 2, g.getFont().getSize());
    }


    @Override
    public void run() {
        double timePerFrame = 1000000000.0 / FPS;
        double timePerUpdate = 1000000000.0 / UPS;
        long previousTime = System.nanoTime();
        long lastCheck = System.currentTimeMillis();
        double deltaU = 0;
        double deltaF = 0;
        int frames = 0;
        int updates = 0;

        while (s.running) {
            long currentTime = System.nanoTime();
            deltaU += (currentTime - previousTime) / timePerUpdate;
            deltaF += (currentTime - previousTime) / timePerFrame;
            previousTime = currentTime;

            if (deltaU >= 1) {
                update(); // move(), checkFood(), checkCollision()
                updates++;
                deltaU--;
            }

            if (deltaF >= 1) {
                gp.repaint(); // Calls paintComponent
                Toolkit.getDefaultToolkit().sync();
                frames++;
                deltaF--;
            }

            if (System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
                System.out.println("FPS: " + frames + " | UPS: " + updates);
                frames = 0;
                updates = 0;
            }

            Thread.yield(); // optional, gives CPU breathing room
        }
    }

    public Food get_food() {
        return food;
    }

    public Snake get_snake(){
        return s;
    }
}
