import javax.swing.*;
import java.awt.*;

public class Game {
    public static Game game = new Game();
    private JFrame frame;
    private GamePanel gamePanel;

    private Game() {
        frame = new JFrame("Arkanoid v0.1");
        frame.setSize(500, 500);
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);


        gamePanel = new GamePanel();
        frame.add(gamePanel);
        frame.setVisible(true);
    }

    /**
     * @return Game() singleton object
     */
    public static Game getInstance() {
        return game;
    }
}
