import java.awt.Graphics2D;
import java.awt.Color;

/**
 * The Menu class is the parent of every possible menu element.
 * @see Menu
 * @see Rect
 * @see Text
 * @see Image
 * @see Button
 * @author David Daniliuc
 * @version "1.8.0_322"
 */
public abstract class MenuItem {
    private int x;
    private int y;
    private Color color;
    //------------------------------------------------------------------------------     
    MenuItem(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    //------------------------------------------------------------------------------ 
    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public Color getColor() {
        return this.color;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setColor(Color color) {
        this.color = color;
    }
    //------------------------------------------------------------------------------ 
    /** 
     * Every child of this class must implement their own custom draw method.
     * @param g2d Graphics2D object
     */
    public abstract void draw(Graphics2D g2d);

    //------------------------------------------------------------------------------ 
    @Override
    public String toString() {
        return "{" +
            " x='" + getX() + "'" +
            ", y='" + getY() + "'" +
            ", color='" + getColor() + "'" +
            "}";
    }
}