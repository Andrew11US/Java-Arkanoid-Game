import java.awt.*;

/**
 * Ball class
 */
public class Ball extends Block implements Paintable {
    public int xDirection;
    public int yDirection;
    public int speed = 1;

    public Ball() {
        super(250,380,Const.BALL_SIZE,Const.BALL_SIZE);
        this.xDirection = 1;
        this.yDirection = 1;
//        this.speed = 1;
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillOval(x, y, Const.BALL_SIZE, Const.BALL_SIZE);
    }
}
