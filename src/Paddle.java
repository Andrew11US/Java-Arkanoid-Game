import java.awt.*;

public class Paddle extends Rectangle {
//    private final int width;
//    private final int height;
//    private int x;
//    private int y;

    public Paddle() {
        this.width = 120;
        this.height = 10;
        this.x = 200;
        this.y = 300;
    }

    public void paint(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(this.x, this.y, this.width, this.height);
        g.drawRect(x,y,width,height);
    }
}
