import java.awt.Graphics2D;
import java.awt.Color;
import java.util.ArrayList;

/**
 * The Menu class is a collection of different menu items that act as a GUI.
 * @see MenuItem
 * @see Rect
 * @see Text
 * @see Image
 * @see Button
 * @author David Daniliuc
 * @version "1.8.0_322"
 */
public class Menu {
    private int x;
    private int y;
    private int width;
    private int height;
    private Color bgColor;

    private MenuItem[] menuItems;
    //------------------------------------------------------------------------------     
    /** 
     * Constructor for Menu class.
     * @param x x coordinate
     * @param y y coordinate
     * @param width width
     * @param height height
     * @param bgColor background Color
     * @param menuItems array of MenuItem
     */
    Menu(int x, int y, int width, int height, Color bgColor, MenuItem[] menuItems) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.bgColor = bgColor;
        this.menuItems = menuItems;
    }
    //------------------------------------------------------------------------------ 
    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
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

    public Color getBgColor() {
        return this.bgColor;
    }

    public void setBgColor(Color bgColor) {
        this.bgColor = bgColor;
    }

    public MenuItem[] getMenuItems() {
        return this.menuItems;
    }

    public void setMenuItems(MenuItem[] menuItems) {
        this.menuItems = menuItems;
    }
    //------------------------------------------------------------------------------   
    public MenuItem getMenuItem(int index) {
        return this.menuItems[index];
    }

    public void setMenuItem(int index, MenuItem menuItem) {
        this.menuItems[index] = menuItem;
    }
    //------------------------------------------------------------------------------   

    /** 
     * Draws the menu onto a Graphics2D panel.
     * @param g2d Graphics2D object
     */
    public void draw(Graphics2D g2d) {
        g2d.setColor(this.bgColor);
        g2d.fillRect(this.x, this.y, this.width, this.height);

        for (int menuItemIndex = 0; menuItemIndex < menuItems.length; menuItemIndex++) {
            menuItems[menuItemIndex].draw(g2d);
        }
    }
    //------------------------------------------------------------------------------   
    /**
     * Resets the color to the original of all the buttons inside the menu.
     */
    public void resetButtons() {
        for (int menuItemIndex = 0; menuItemIndex < menuItems.length; menuItemIndex++) {
            if (menuItems[menuItemIndex] instanceof Button) {
                ((Button) menuItems[menuItemIndex]).resetColor();
            }
        }
    }
    //------------------------------------------------------------------------------   
    /** 
     * Find all the buttons inside the menu that collided with the passed coordinate.
     * @param x x coordinate
     * @param y y coordinate
     * @return ArrayList<Integer>
     */
    public ArrayList < Integer > findCollidedButtons(int x, int y) {
        ArrayList < Integer > collidedButtons = new ArrayList < Integer > ();

        for (int menuItemIndex = 0; menuItemIndex < menuItems.length; menuItemIndex++) {
            if (menuItems[menuItemIndex] instanceof Button) {
                Button menuButton = (Button) menuItems[menuItemIndex];

                if (menuButton.inside(x, y)) {
                    collidedButtons.add(menuItemIndex);
                }
            }
        }
        return collidedButtons;
    }
    //------------------------------------------------------------------------------   
    @Override
    public String toString() {
        return "{" +
            " x='" + getX() + "'" +
            ", y='" + getY() + "'" +
            ", width='" + getWidth() + "'" +
            ", height='" + getHeight() + "'" +
            ", bgColor='" + getBgColor() + "'" +
            ", menuItems='" + getMenuItems() + "'" +
            "}";
    }

}