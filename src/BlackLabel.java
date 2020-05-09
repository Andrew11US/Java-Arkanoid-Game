import javax.swing.*;
import java.awt.*;
// Custom black label for dark view
public class BlackLabel extends JLabel {
    // BlackLabel constructor
    public BlackLabel(String text) {
        super(text);
        setHorizontalAlignment(SwingConstants.CENTER);
        setFont(new Font("Calibri",Font.BOLD,32));
        setForeground(Color.WHITE);
    }
}
