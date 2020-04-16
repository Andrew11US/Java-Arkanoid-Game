import java.awt.*;

/**
 * Ball class
 */
public class Ball extends Block implements Paintable {
    public int speed = 3;
    public int xDirection;
    public int yDirection;

    public Ball() {
        super(250,380,Const.BALL_SIZE,Const.BALL_SIZE);
        this.xDirection = -1;
        this.yDirection = -1;
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillOval(x, y, Const.BALL_SIZE, Const.BALL_SIZE);
    }
}
