import java.awt.*;
import java.util.Random;

/**
 * Ball class inherited from Block
 */
public class Ball extends Block implements Paintable {
    public int xDirection;
    public int yDirection;
    public int speed = 1;
    public int size;
    Random random = new Random();

    public Ball() {
        super(Const.WINDOW_WIDTH / 2,
                Const.WINDOW_HEIGHT / 2,
                Const.BALL_SIZE,
                Const.BALL_SIZE);
        this.xDirection = 1;
        this.yDirection = -1;
        size = width;
        // MARK: generating random ball position
        int rightBound = (Const.WINDOW_WIDTH / 2) + Const.PADDLE_WIDTH;
        int leftBound = (Const.WINDOW_WIDTH / 2) - Const.PADDLE_WIDTH;
        int topBound = Const.WINDOW_HEIGHT - 200;
        int bottomBound = Const.WINDOW_HEIGHT - 80;

        x = random.nextInt((rightBound -leftBound))+leftBound;
        y = random.nextInt((bottomBound-topBound))+topBound;

//        System.out.println(rightBound);
//        System.out.println(leftBound);
//        System.out.println(topBound);
//        System.out.println(bottomBound);
    }

    @Override
    public void paint(Graphics g) {
        if (speed == 0) {
            g.setColor(Color.GRAY);
        } else {
            g.setColor(Color.GREEN);
        }

        g.fillOval(x, y, Const.BALL_SIZE, Const.BALL_SIZE);
    }
}
