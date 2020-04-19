import javax.swing.*;
import java.awt.*;

public class BlackLabel extends JLabel {

    public BlackLabel(String text) {
        super(text);
        setHorizontalAlignment(SwingConstants.CENTER);
        setFont(new Font("Calibri",Font.BOLD,32));
        setForeground(Color.WHITE);
    }
}
