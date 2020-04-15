import java.awt.*;

public class Ball {
    private int x, y;
    private int size = 20;
    private int speed;
    private int dirX;
    private int dirY;

    public Ball() {
        this.x = 250;
        this.y = 250;
        this.speed = 1;
        this.dirX = 0;
        this.dirY = 1;
    }

    public void paint(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillOval(this.x, this.y, this.size, this.size);
    }
}
