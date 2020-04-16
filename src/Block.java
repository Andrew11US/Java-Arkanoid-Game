import java.awt.*;

public class Block extends Rectangle {
//    private int x;
//    private int y;
//    private int width;
//    private int height;
    public boolean destroyed = false;

    public Block(int x, int y) {
        this.x = x;
        this.y = y;
        this.width = 50;
        this.height = 30;
    }

    public void paint(Graphics g) {
        if (!destroyed) {
            g.fillRect(this.x, this.y, this.width, this.height);
            g.setColor(Color.BLACK);
            g.drawRect(this.x, this.y, this.width, this.height);
        }
    }
}
