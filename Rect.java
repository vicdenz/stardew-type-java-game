import java.awt.Graphics2D;
import java.awt.Color;

/**
 * The Rect class is a template for making blank rectangles.
 * The Rect class inherits from MenuItem.
 * @see MenuItem
 * @see Image
 * @author David Daniliuc
 * @version "1.8.0_322"
 */
public class Rect extends MenuItem {
    private int width;
    private int height;
    private int borderWidth;
    private Color borderColor;
    //------------------------------------------------------------------------------    
    Rect(int x, int y, Color color, int width, int height) {
        super(x, y, color);
        this.width = width;
        this.height = height;
        this.borderWidth = 0;
    }
    //------------------------------------------------------------------------------    
    Rect(int x, int y, Color color, int width, int height, int borderWidth, Color borderColor) {
        super(x, y, color);
        this.width = width;
        this.height = height;
        this.borderWidth = borderWidth;
        this.borderColor = borderColor;
    }

    //------------------------------------------------------------------------------ 
    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public int getBorderWidth() {
        return this.width;
    }

    public Color getBorderColor() {
        return this.borderColor;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setBorderWidth(int borderWidth) {
        this.borderWidth = borderWidth;
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }
    //------------------------------------------------------------------------------  
    /** 
     * Centers the middle of the text to the passed coordinate.
     * @param x x coordinate
     * @param y y coordinate
     */
    public void centerRect(int x, int y) {
        this.setX((int)(x - this.width / 2));
        this.setY((int)(y - this.height / 2));
    }
    //------------------------------------------------------------------------------  
    /** 
     * Draws the rect onto a Graphics2D panel.
     * @param g2d Graphics2D object
     */
    public void draw(Graphics2D g2d) {
        if (this.borderWidth > 0) {
            g2d.setColor(this.borderColor);
            g2d.fillRect(this.getX() - this.borderWidth, this.getY() - this.borderWidth, this.width + this.borderWidth * 2, this.height + this.borderWidth * 2);
        }

        g2d.setColor(this.getColor());
        g2d.fillRect(this.getX(), this.getY(), this.width, this.height);
    }
    //------------------------------------------------------------------------------  
    @Override
    public String toString() {
        return super.toString() + "\b," +
            " width='" + getWidth() + "'" +
            ", height='" + getHeight() + "'" +
            ", borderWidth='" + getBorderWidth() + "'" +
            ", borderColor='" + getBorderColor() + "'" +
            "}";
    }

}