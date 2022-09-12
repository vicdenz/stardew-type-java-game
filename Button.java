import java.awt.Graphics2D;
import java.awt.Color;

/**
 * The Button class is a template for making buttons. It works with the main program to add functionality. 
 * The Button class inherits from MenuItem. 
 * @see Rect
 * @see Text
 * @see MenuItem
 * @author David Daniliuc
 * @version "1.8.0_322"
 */
public class Button extends MenuItem {
    private Rect rect;
    private Text text;

    private int buttonEventType;
    private int buttonFunction;

    private Color originalColor;
    private Color hoveredColor;

    private int margin;
    //------------------------------------------------------------------------------    
    Button(Rect rect, Text text, int buttonEventType, int buttonFunction, Color hoveredColor, boolean inheritSize, boolean isCentered, int margin) {
        super(rect.getX(), rect.getY(), rect.getColor());
        this.rect = rect;
        this.text = text;
        this.margin = margin;

        this.originalColor = this.text.getColor();
        this.hoveredColor = hoveredColor;

        this.buttonEventType = buttonEventType;
        this.buttonFunction = buttonFunction;

        if (inheritSize) {
            this.rect.setWidth(this.text.getWidth() + margin * 2);
            this.rect.setHeight(this.text.getHeight() + margin * 2);
        }

        this.rect.setX(this.rect.getX() - this.margin);
        this.rect.setY(this.rect.getY() - this.margin);

        if (isCentered) {
            this.text.centerText(this.rect);
        } else {
            this.text.setX(this.rect.getX());
            this.text.setY(this.rect.getY());
        }
    }
    //------------------------------------------------------------------------------  
    public Rect getRect() {
        return this.rect;
    }

    public void setRect(Rect rect) {
        this.rect = rect;
    }

    public Text getText() {
        return this.text;
    }

    public void setText(Text text) {
        this.text = text;
    }

    public int getButtonEventType() {
        return this.buttonEventType;
    }

    public void setButtonEventType(int buttonEventType) {
        this.buttonEventType = buttonEventType;
    }

    public int getButtonFunction() {
        return this.buttonFunction;
    }

    public void setButtonFunction(int buttonFunction) {
        this.buttonFunction = buttonFunction;
    }

    public Color getOriginalColor() {
        return this.originalColor;
    }

    public void setOriginalColor(Color originalColor) {
        this.originalColor = originalColor;
    }

    public Color getHoveredColor() {
        return this.hoveredColor;
    }

    public void setHoveredColor(Color hoveredColor) {
        this.hoveredColor = hoveredColor;
    }

    public int getMargin() {
        return this.margin;
    }

    public void setMargin(int margin) {
        this.margin = margin;
    }
    //------------------------------------------------------------------------------  
    public void resetColor() {
        this.text.setColor(originalColor);
    }
    //------------------------------------------------------------------------------  
    public void hoveredColor() {
        this.text.setColor(hoveredColor);
    }
    //------------------------------------------------------------------------------      
    /** 
     * Centers the middle of the button to the passed coordinate.
     * @param x x coordinate
     * @param y y coordinate
     */
    public void centerButton(int x, int y) {
        this.rect.setWidth(this.text.getWidth() + margin * 2);
        this.rect.setHeight(this.text.getHeight() + margin * 2);

        this.setX((int)(x - this.rect.getWidth() / 2));
        this.setY((int)(y - this.rect.getHeight() / 2));

        this.rect.centerRect(x, y);
        this.text.centerText(this.rect);
    }

    /** 
     * Returns true if the passed coordinate collides with the button. 
     * @param x x coordinate
     * @param y y coordinate
     * @return boolean
     */
    //------------------------------------------------------------------------------     
    public boolean inside(int x, int y) {
        return (x > this.getX() && x < this.getX() + this.rect.getWidth() && y > this.getY() && y < this.getY() + this.rect.getHeight());
    }
    //------------------------------------------------------------------------------      
    /** 
     * Check if the passed mouseEvent type is the button's mouseEvent type. 
     * @param mouseEventType - game mouse event type
     * @return boolean
     */
    public boolean checkButtonType(int mouseEventType) {
        return (this.buttonEventType == mouseEventType);
    }
    //------------------------------------------------------------------------------      
    /** 
     * Draws the button onto a Graphics2D panel.
     * @param g2d Graphics2D object
     */
    public void draw(Graphics2D g2d) {
        this.rect.draw(g2d);
        this.text.draw(g2d);
    }

    //------------------------------------------------------------------------------      
    @Override
    public String toString() {
        return super.toString() + "\b," +
            " rect='" + getRect() + "'" +
            ", text='" + getText() + "'" +
            ", buttonEventType='" + getButtonEventType() + "'" +
            ", buttonFunction='" + getButtonFunction() + "'" +
            ", originalColor='" + getOriginalColor() + "'" +
            ", hoveredColor='" + getHoveredColor() + "'" +
            ", margin='" + getMargin() + "'" +
            "}";
    }

}