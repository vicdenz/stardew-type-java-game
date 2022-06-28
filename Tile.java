import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * The Tile class is a individual tileset image on the layer grid.
 * It can have collsion enabled or not.
 * @see TileLayer
 * @author David Daniliuc
 * @version "1.8.0_322"
 */
public class Tile {
    private int x;
    private int y;
    private BufferedImage image;
    private boolean collision;
    //------------------------------------------------------------------------------    
    Tile(int row, int col, BufferedImage image, boolean collision) {
        this.x = col * Const.TILE_SIZE;
        this.y = row * Const.TILE_SIZE;
        this.image = image;
        this.collision = collision;
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

    public BufferedImage getImage() {
        return this.image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public boolean isCollision() {
        return this.collision;
    }

    public boolean getCollision() {
        return this.collision;
    }

    public void setCollision(boolean collision) {
        this.collision = collision;
    }
    //------------------------------------------------------------------------------    
    public boolean collides(int x, int y) {
        return (x > this.x && x < this.x + Const.TILE_SIZE && y > this.y && y < this.y + Const.TILE_SIZE);
    }
    //------------------------------------------------------------------------------    
    public void draw(Graphics2D g2d) {
        g2d.drawImage(this.image, this.getX(), this.getY(), null);
    }

    public void draw(Graphics2D g2d, int[] scroll) {
        g2d.drawImage(this.image, this.getX() + scroll[0], this.getY() + scroll[1], null);
    }
    //------------------------------------------------------------------------------    
    @Override
    public String toString() {
        return "{" +
            " x='" + getX() + "'" +
            ", y='" + getY() + "'" +
            ", image='" + getImage() + "'" +
            ", collision='" + isCollision() + "'" +
            "}";
    }
}