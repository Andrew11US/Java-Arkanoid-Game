import javax.swing.*;
import java.awt.*;

public class ScoreLabel extends JLabel {
    public ScoreLabel(String text) {
        super();
        setOpaque(true);
        setText(text);
    }

    public void paint(Graphics g) {
        super.paint(g);
    }
}
