import java.awt.Graphics2D;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * The Image class is a template for making images.
 * The Image class inherits from Rect(also a child of MenuItem).
 * @see MenuItem
 * @see Rect
 * @author David Daniliuc
 * @version "1.8.0_322"
 */
public class Image extends Rect {
    private BufferedImage image;

    //------------------------------------------------------------------------------    
    Image(int x, int y, String imagePath) {
        super(x, y, Const.BLACK_COLOR, 0, 0);

        try {
            this.image = ImageIO.read(new File(imagePath));
        } catch (IOException ex) {
            System.out.println("File not found!");
        }
        this.setWidth(this.image.getWidth());
        this.setHeight(this.image.getHeight());
    }
    //------------------------------------------------------------------------------  
    public BufferedImage getImage() {
        return this.image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    //------------------------------------------------------------------------------  
    /** 
     * Resets the status and color of this tile.
     * @param g2d Graphics2D object
     */
    public void draw(Graphics2D g2d) {
        g2d.drawImage(this.image, this.getX(), this.getY(), null);
    }
    //------------------------------------------------------------------------------  
    @Override
    public String toString() {
        return super.toString() + "\b," +
            " image='" + getImage() + "'" +
            "}";
    }
}