import javax.swing.*;
import java.awt.*;
// Custom black button for dark view
public class BlackButton extends JButton {
    // BlackButton constructor
    public BlackButton(String text, Color color) {
        super(text);
        setFont(new Font("Calibri",Font.BOLD,22));
        setForeground(color);
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
    }
}
