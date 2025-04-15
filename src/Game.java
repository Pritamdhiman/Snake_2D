import java.awt.*;

public class Game implements Runnable {
    private GamePanel gp;
    private Game_window gw;
    private Thread game_thread;
    final static int FPS = 60;
    final static int UPS = 60;

    public Game(){
        gp = new GamePanel(this);
        gw = new Game_window(gp);
        gp.requestFocus();
        start_game_loop();
    }

    private void start_game_loop(){
        gp.newFood();
        gp.running = true;
        game_thread = new Thread(this);
        game_thread.start();
    }

    public void update(){
        // Update everything like, game_panel, MC, NPC, etc.
        gp.update();
    }

    public void render(Graphics g){
        // Render everything like, game_panel, MC, NPC, etc.
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

        while (gp.running) {
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
}
