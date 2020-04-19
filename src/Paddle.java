import java.awt.*;

/**
 * Paddle class inherited from Block
 */
public class Paddle extends Block implements Paintable {

    // MARK: Generation Paddle x position in the middle regardless Window_Size
    public Paddle() {
        super((Const.WINDOW_WIDTH / 2)-(Const.PADDLE_WIDTH / 2),
                Const.WINDOW_HEIGHT - 50,
                Const.PADDLE_WIDTH,
                Const.PADDLE_HEIGHT);
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRoundRect(x,y,width,height,Const.PADDLE_HEIGHT,Const.PADDLE_HEIGHT);
    }
}
