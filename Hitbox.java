import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Color;

/**
* The Hitbox class is a class that checks for collision between itself and other Hitboxes.
* @see Sprite
* @see Map
* @author David Daniliuc
* @version "1.8.0_322"
*/
public class Hitbox {
    private Color color = new Color(255, 0, 0);

    private int x;
    private int y;
    private int width;
    private int height;
    private int margin;
    private Rectangle rect;
    //------------------------------------------------------------------------------       
    Hitbox(int x, int y) {
        this.x = x;
        this.y = y;
        this.width = Const.TILE_SIZE;
        this.height = Const.TILE_SIZE;
        this.rect = new Rectangle(this.x, this.y, this.width, this.height);
    }
    //------------------------------------------------------------------------------       
    Hitbox(int x, int y, int margin) {
        this.x = x + margin;
        this.y = y + margin;
        this.width = Const.TILE_SIZE - margin * 2;
        this.height = Const.TILE_SIZE - margin;
        this.margin = margin;
        this.rect = new Rectangle(this.x, this.y, this.width, this.height);
    }
    //------------------------------------------------------------------------------       
    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
        this.rect.setLocation(this.x, this.y);
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
        this.rect.setLocation(this.x, this.y);
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
        this.rect.setSize(this.width, this.height);
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
        this.rect.setSize(this.width, this.height);
    }

    public int getMargin() {
        return this.margin;
    }

    public void setMargin(int margin) {
        this.margin = margin;
    }

    public Rectangle getRect() {
        return this.rect;
    }

    public void setRect(Rectangle rect) {
        this.rect = rect;
    }

    public Color getColor() {
        return this.color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
    //------------------------------------------------------------------------------       	    
    public int getTop() {
        return this.y;
    }
    public int getBottom() {
        return this.y + this.height;
    }
    public int getLeft() {
        return this.x;
    }
    public int getRight() {
        return this.x + this.width;
    }
    public int[] getCenter() {
        int[] center = {
            this.x + (this.width / 2),
            this.y + (this.height / 2)
        };
        return center;
    }

    public void setTop(int y) {
        this.setY(y);
    }
    public void setBottom(int y) {
        this.setY(y - this.height);
    }
    public void setLeft(int x) {
        this.setX(x);
    }
    public void setRight(int x) {
        this.setX(x - this.width);
    }
    public void setCenter(int x, int y) {
        int[] center = getCenter();
        this.setX(this.x + (x - center[0]));
        this.setY(this.y + (y - center[1]));
    }
    //------------------------------------------------------------------------------       	    
    public boolean collide(Hitbox hitbox) {
        return this.rect.intersects(hitbox.rect);
    }
    //------------------------------------------------------------------------------       	    
    public void draw(Graphics2D g2d) {
        g2d.setColor(this.color);
        g2d.fillRect(this.x, this.y, this.width, this.height);
    }
    //------------------------------------------------------------------------------       	    
    @Override
    public String toString() {
        return "{" +
            " color='" + getColor() + "'" +
            ", x='" + getX() + "'" +
            ", y='" + getY() + "'" +
            ", width='" + getWidth() + "'" +
            ", height='" + getHeight() + "'" +
            ", margin='" + getMargin() + "'" +
            ", rect='" + getRect() + "'" +
            "}";
    }
}