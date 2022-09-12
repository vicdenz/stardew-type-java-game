import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;

import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;

/**
 * The Text class is a template for making text with custom fonts.
 * The Text class inherits from MenuItem.
 * @see MenuItem
 * @see Image
 * @see CustomFont
 * @author David Daniliuc
 * @version "1.8.0_322"
 */
public class Text extends MenuItem {
    private String text;
    private Font font;
    private int width;
    private int height;
    private FontRenderContext frc = new FontRenderContext(new AffineTransform(), true, true);
    //------------------------------------------------------------------------------    
    Text(int x, int y, String text, Font font, Color color) {
        super(x, y, color);
        this.text = text;
        this.font = font;

        // Calculate the width and height of the text with the current Font.
        this.width = (int)(this.font.getStringBounds(this.text, this.frc).getWidth());
        this.height = (int)(this.font.getStringBounds(this.text, this.frc).getHeight());
    }
    //------------------------------------------------------------------------------ 
    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;

        // Calculate the width and height of the text with the current Font.
        this.width = (int)(this.font.getStringBounds(this.text, this.frc).getWidth());
        this.height = (int)(this.font.getStringBounds(this.text, this.frc).getHeight());
    }

    public Font getFont() {
        return this.font;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public FontRenderContext getFrc() {
        return this.frc;
    }

    public void setFrc(FontRenderContext frc) {
        this.frc = frc;
    }
    //------------------------------------------------------------------------------  

    /** 
     * Centers the middle of the text to the passed coordinate.
     * @param x x coordinate
     * @param y y coordinate
     */
    public void centerText(int x, int y) {
        this.setX((int)(x - this.width / 2));
        this.setY((int)(y - this.height / 2));
    }

    /** 
     * Centers the middle of the text to the middle of the passed rect.
     * @param rect Rect object
     */
    public void centerText(Rect rect) {
        this.setX((int)(rect.getX() + rect.getWidth() / 2 - this.width / 2));
        this.setY((int)(rect.getY() + rect.getHeight() / 2 - this.height / 2));
    }
    //------------------------------------------------------------------------------  

    /** 
     * Draws the rect onto a Graphics2D panel.
     * @param g2d Graphics2D object
     */
    public void draw(Graphics2D g2d) {
        g2d.setColor(this.getColor());
        g2d.setFont(this.font);

        g2d.drawString(this.text, this.getX(), this.getY() + this.height);
    }
    //------------------------------------------------------------------------------  
    @Override
    public String toString() {
        return super.toString() + "\b," +
            " text='" + getText() + "'" +
            ", font='" + getFont() + "'" +
            ", width='" + getWidth() + "'" +
            ", height='" + getHeight() + "'" +
            ", frc='" + getFrc() + "'" +
            "}";
    }

}