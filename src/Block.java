import java.awt.*;

/**
 * Abstract class Block is a wrapper for Rectangle library class
 */
abstract class Block extends Rectangle {

    private int top;
    private int bottom;
    private int left;
    private int right;

    public Block(int x, int y, int width, int height) {
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        top = y;
        bottom = y + height;
        left = x;
        right = x + width;
    }

    /**
     * @return Block Top Edge
     */
    public int getTop() {
        return top;
    }

    /**
     * @return Block Bottom Edge
     */
    public int getBottom() {
        return bottom;
    }

    /**
     * @return Block Left Edge
     */
    public int getLeft() {
        return left;
    }

    /**
     * @return Block Right Edge
     */
    public int getRight() {
        return right;
    }
}
