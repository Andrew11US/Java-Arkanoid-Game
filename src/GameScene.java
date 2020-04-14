import javax.swing.*;
import java.awt.*;

public class GameScene {
    public static GameScene gameScene = new GameScene();
    private static JFrame frame;

    GameScene() {
        this.frame = new JFrame("Arkanoid v0.1");
        this.frame.setSize(500, 500);
        this.frame.setLayout(new BorderLayout());
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setResizable(false);
        this.frame.setVisible(true);
    }

    /**
     * @return gameScene singleton object
     */
    public static GameScene getInstance() {
        return gameScene;
    }
}
