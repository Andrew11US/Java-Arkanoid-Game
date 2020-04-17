import java.awt.*;

/**
 * Brick class
 */
public class Brick extends Block implements Paintable {
    public boolean destroyed = false;

    public Brick(int x, int y) {
        super(x, y,Const.BRICK_WIDTH,Const.BRICK_HEIGHT);
    }

    @Override
    public void paint(Graphics g) {
        if (!destroyed) {
            g.setColor(Color.GRAY);
            g.fillRect(this.x, this.y, this.width, this.height);
            g.setColor(Color.BLACK);
            g.drawRect(this.x, this.y, this.width, this.height);
        }
    }
}
