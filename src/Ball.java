import java.awt.*;
import java.util.Random;

/**
 * Ball class
 */
public class Ball extends Block implements Paintable {
    public int xDirection;
    public int yDirection;
    public int speed = 1;
    public int size;
    Random random = new Random();

    public Ball() {
        super(250,250,Const.BALL_SIZE,Const.BALL_SIZE);
        this.xDirection = 1;
        this.yDirection = -1;
        size = width;
        // MARK: generating random ball position
        x = random.nextInt((300-200)+1)+200;
        y = random.nextInt((300-250)+1)+250;
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillOval(x, y, Const.BALL_SIZE, Const.BALL_SIZE);
    }
}
