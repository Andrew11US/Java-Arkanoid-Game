import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Game implements KeyListener {
    public static Game game = new Game();
    private JFrame frame;
    private GamePanel gamePanel;
    private Thread thread;
    private boolean isRunning = false;

    private Game() {
        frame = new JFrame("Arkanoid v0.3");
        frame.setSize(500, 500);
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);


        gamePanel = new GamePanel();
        frame.add(gamePanel);
        frame.setVisible(true);

        run();

    }

    private void run() {
//        thread = new Thread(()->{
//            while (true) {
//                gamePanel.update();
//
//                try {
//                    Thread.sleep(10);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//        thread.start();

    }

    /**
     * @return Game() singleton object
     */
    public static Game getInstance() {
        return game;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            System.out.println("Enter");
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
