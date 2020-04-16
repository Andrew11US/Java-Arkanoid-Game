import java.awt.*;

public class Ball extends Block {
//    private int x, y;
    private int size = 20;
    public int speed = -1;
    public int moveX;
    public int moveY;

    public Ball() {
        super(250,250);
        this.x = 250;
        this.y = 250;
//        this.speed = 1;
        this.moveX = speed;
        this.moveY = speed;
    }

    public void paint(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillOval(this.x, this.y, this.size, this.size);
    }
}
