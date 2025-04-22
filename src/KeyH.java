import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
public class KeyH extends KeyAdapter {
    Snake s;

    public KeyH(Snake s){
        this.s = s;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                if (s.dir != 'r') s.dir = 'l';
                break;
            case KeyEvent.VK_RIGHT:
                if (s.dir != 'l') s.dir = 'r';
                break;
            case KeyEvent.VK_UP:
                if (s.dir != 'd') s.dir = 'u';
                break;
            case KeyEvent.VK_DOWN:
                if (s.dir != 'u') s.dir = 'd';
                break;
        }
    }
}