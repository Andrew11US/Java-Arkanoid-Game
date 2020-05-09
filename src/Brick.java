import java.awt.*;

/**
 * Brick class inherited from Block
 */
public class Brick extends Block implements Paintable {
    public boolean destroyed = false;
    // Brick constructor
    public Brick(int x, int y) {
        super(x, y,Const.BRICK_WIDTH,Const.BRICK_HEIGHT);
    }

    // Implementing paint method for graphic objects
    @Override
    public void paint(Graphics g) {
        if (!destroyed) {
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(this.x, this.y, this.width, this.height);
            g.setColor(Color.WHITE);
            g.drawRect(this.x, this.y, this.width, this.height);
        }
    }
}
