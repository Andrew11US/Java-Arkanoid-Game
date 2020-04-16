import java.awt.*;

/**
 * Abstract class Block is a wrapper for Rectangle library class
 */
abstract class Block extends Rectangle {

    public Block(int x, int y, int width, int height) {
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
    }

}
