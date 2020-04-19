import javax.swing.*;
import java.awt.*;

public class BlackButton extends JButton {
    public BlackButton(String text, Color color) {
        super(text);
        setFont(new Font("Calibri",Font.BOLD,22));
        setForeground(color);
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
    }
}
