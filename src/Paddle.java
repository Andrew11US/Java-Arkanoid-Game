import java.awt.*;

/**
 * Paddle class inherited from Block
 */
public class Paddle extends Block implements Paintable {

    public Paddle() {
        super(200,400,Const.PADDLE_WIDTH,Const.PADDLE_HEIGHT);
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRoundRect(x,y,width,height,Const.PADDLE_HEIGHT,Const.PADDLE_HEIGHT);
    }
}
