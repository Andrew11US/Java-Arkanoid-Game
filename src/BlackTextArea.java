import javax.swing.*;
import java.awt.*;
// Custom black text area
public class BlackTextArea extends JTextArea {
    // BlackTextArea constructor
    public BlackTextArea(int rows, int columns) {
        super(rows, columns);
        setForeground(Color.WHITE);
        setFont(new Font("Calibri",Font.BOLD,22));
        setMargin(new Insets(10,Const.WINDOW_WIDTH/2 - 100,10,10));
        setOpaque(false);
        setEditable(false);
    }
}
