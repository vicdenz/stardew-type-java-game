import java.awt.Graphics2D;

import java.io.File;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

// possible exceptions
import java.io.IOException;
import java.awt.image.RasterFormatException;

/**
 * The Sprite is a class that renders animated characters, directionally and state dependant.
 * @see Main
 * @see Map
 * @see NPC
 * @see Player
 * @author David Daniliuc
 * @version "1.8.0_322"
 */
public class Sprite {
    public static int numOfStates = 2;
    public static int numOfDirections = 4;
    public static int numOfFrames = 6;

    private String name;
    private int x;
    private int y;
    private int width;
    private int height;
    private Hitbox hitbox;

    private int state; // 0 = idle, 1 = run
    private String direction;
    private int imageFrame;
    private BufferedImage spriteSheet;
    private BufferedImage[][][] spriteImages;
    //------------------------------------------------------------------------------       
    Sprite(String name, int x, int y, String spriteSheetName) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.width = Const.TILE_SIZE;
        this.height = Const.SPRITE_HEIGHT;
        this.hitbox = new Hitbox(this.x, this.y + this.height - Const.TILE_SIZE);

        this.state = 0;
        this.direction = "S";
        this.imageFrame = 0;

        try {
            this.spriteSheet = ImageIO.read(new File(spriteSheetName));
        } catch (IOException ex) {
            System.out.println("Unable to load sprite sheet image. " + spriteSheetName);
        }

        this.spriteImages = new BufferedImage[Sprite.numOfStates][Sprite.numOfDirections][Sprite.numOfFrames];
        try {
            this.spriteImages[0] = this.loadSpriteRow(1);
            this.spriteImages[1] = this.loadSpriteRow(2);
        } catch (RasterFormatException ex) {
            System.out.println("Unable to parse sprite sheet image. " + spriteSheetName);
        }
    }
    //------------------------------------------------------------------------------       
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public Hitbox getHitbox() {
        return this.hitbox;
    }

    public void setHitbox(Hitbox hitbox) {
        this.hitbox = hitbox;
    }

    public int getState() {
        return this.state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getDirection() {
        return this.direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public int getImageFrame() {
        return this.imageFrame;
    }

    public void setImageFrame(int imageFrame) {
        this.imageFrame = imageFrame;
    }

    public BufferedImage getSpriteSheet() {
        return this.spriteSheet;
    }

    public void setSpriteSheet(BufferedImage spriteSheet) {
        this.spriteSheet = spriteSheet;
    }

    public BufferedImage[][][] getSpriteImages() {
        return this.spriteImages;
    }

    public void setSpriteImages(BufferedImage[][][] spriteImages) {
        this.spriteImages = spriteImages;
    }
    //------------------------------------------------------------------------------       	    
    /**
     * Set the hitbox coords to the actual coords of the sprite
     */
    public void syncHitboxToCoord() {
        this.hitbox.setX(this.x);
        this.hitbox.setY(this.y + this.height - Const.TILE_SIZE);
    }

    /**
     * Set the actual coords of the sprite to the hitbox coords
     */
    public void syncCoordToHitbox() {
        this.x = this.hitbox.getX();
        this.y = this.hitbox.getY() - this.height + Const.TILE_SIZE;
    }
    //------------------------------------------------------------------------------       	    
    /**
     * Convert the string format of this.direction to the array index of that direction in the spritesheet.
     * @return int
     */
    public int directionToIndex() {
        // 0 = right, 1 = up, 2 = left, 3 = down
        if (this.direction.equalsIgnoreCase("N")) {
            return 1;
        } else if (this.direction.equalsIgnoreCase("S")) {
            return 3;
        } else if (this.direction.equalsIgnoreCase("E")) {
            return 0;
        } else if (this.direction.equalsIgnoreCase("W")) {
            return 2;
        } else if (this.direction.equalsIgnoreCase("NW")) {
            return 2;
        } else if (this.direction.equalsIgnoreCase("NE")) {
            return 0;
        } else if (this.direction.equalsIgnoreCase("SW")) {
            return 2;
        } else if (this.direction.equalsIgnoreCase("SE")) {
            return 0;
        } else {
            return 3;
        }
    }
    //------------------------------------------------------------------------------       	    
    public BufferedImage[][] loadSpriteRow(int row) {
        BufferedImage[][] spriteRow = new BufferedImage[Sprite.numOfDirections][Sprite.numOfFrames];

        int spacing = 16;
        for (int col = 0; col < (Sprite.numOfDirections * Sprite.numOfFrames); col++) {
            spriteRow[col / Sprite.numOfFrames][col % Sprite.numOfFrames] = this.spriteSheet.getSubimage(col * this.width, (row) * (this.height) + (row + 1) * spacing, this.width, this.height);
        }
        return spriteRow;
    }
    //------------------------------------------------------------------------------       	    
    public void updateFrame(int state) {
        if (this.state != state) {
            this.state = state;
            this.imageFrame = 0;
        } else {
            this.imageFrame = (this.imageFrame + 1) % Const.FPS;
        }
    }
    //------------------------------------------------------------------------------       	    
    public BufferedImage getCurrentSpriteImage() {
        return this.spriteImages[this.state][this.directionToIndex()][this.imageFrame / (Const.FPS / Sprite.numOfFrames)];
    }
    //------------------------------------------------------------------------------       	    
    public void draw(Graphics2D g2d) {
        g2d.drawImage(this.getCurrentSpriteImage(), this.x, this.y, null);
    }
    //------------------------------------------------------------------------------       	    
    public void draw(Graphics2D g2d, int[] scroll) {
        g2d.drawImage(this.getCurrentSpriteImage(), this.x + scroll[0], this.y + scroll[1], null);
    }

    public void drawHitbox(Graphics2D g2d) {
        this.hitbox.draw(g2d);
    }

    public void drawSpriteSheet(Graphics2D g2d) {
        for (int spriteState = 0; spriteState < this.spriteImages.length; spriteState++) {
            for (int spriteDirection = 0; spriteDirection < this.spriteImages[spriteState].length; spriteDirection++) {
                for (int spriteFrame = 0; spriteFrame < this.spriteImages[spriteState][spriteDirection].length; spriteFrame++) {
                    g2d.drawImage(this.spriteImages[spriteState][spriteDirection][spriteFrame], spriteState * (this.width * (Sprite.numOfFrames + 1)) + spriteFrame * this.width, spriteDirection * this.height, null);
                }
            }
        }
    }
    //------------------------------------------------------------------------------       	    
    @Override
    public String toString() {
        return "{" +
            " name='" + getName() + "'" +
            ", x='" + getX() + "'" +
            ", y='" + getY() + "'" +
            ", width='" + getWidth() + "'" +
            ", height='" + getHeight() + "'" +
            ", hitbox='" + getHitbox() + "'" +
            ", state='" + getState() + "'" +
            ", direction='" + getDirection() + "'" +
            ", imageFrame='" + getImageFrame() + "'" +
            ", spriteSheet='" + getSpriteSheet() + "'" +
            ", spriteImages='" + getSpriteImages() + "'" +
            "}";
    }
}