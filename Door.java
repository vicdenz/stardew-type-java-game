import java.awt.Graphics2D;

import java.io.File;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

// possible exceptions
import java.io.IOException;
import java.awt.image.RasterFormatException;

/**
 * The Door is a class that check for interaction between the player and the objects, 
 * and when the criteria is met, it changes the game map.
 * @see NPC
 * @author David Daniliuc
 * @version "1.8.0_322"
 */
public class Door extends InteractiveObject {
    private int x;
    private int y;
    private int width;
    private int height;

    private int imageFrame;
    private int numOfFrames;
    private BufferedImage spriteSheet;
    private BufferedImage[] spriteImages;

    private int mapEntered;
    private int roomX;
    private int roomY;
    private String entryRequirement;
    private boolean extendedRequirement;
    //------------------------------------------------------------------------------       
    public Door(String spriteSheetName, String activationKey, int x, int y, int roomX, int roomY, int mapEntered, String entryRequirement, boolean extendedRequirement) {
        super(activationKey);
        this.x = x;
        this.y = y;

        this.mapEntered = mapEntered;
        this.roomX = roomX;
        this.roomY = roomY;
        this.entryRequirement = entryRequirement;
        this.extendedRequirement = extendedRequirement;

        // same as Sprite image loader, just it only loads one image row.
        this.width = Const.TILE_SIZE;
        if (spriteSheetName == null) {
            this.height = Const.TILE_SIZE;
            this.numOfFrames = 1;
            this.spriteImages = new BufferedImage[this.numOfFrames];

            this.spriteImages[0] = new BufferedImage(this.width, this.height, BufferedImage.TYPE_4BYTE_ABGR);
        } else {
            try {
                this.spriteSheet = ImageIO.read(new File(spriteSheetName));
                this.numOfFrames = spriteSheet.getWidth() / Const.TILE_SIZE;
                this.height = spriteSheet.getHeight();

            } catch (IOException ex) {
                System.out.println("Unable to load sprite sheet image. " + spriteSheetName);
            }

            this.spriteImages = new BufferedImage[this.numOfFrames];
            try {
                for (int col = 0; col < this.spriteImages.length; col++) {
                    spriteImages[col] = this.spriteSheet.getSubimage(col * this.width, 0, this.width, this.height);
                }
            } catch (RasterFormatException ex) {
                System.out.println("Unable to parse sprite sheet image. " + spriteSheetName);
            }
        }
    }
    //------------------------------------------------------------------------------       
    public int[] getCenter() {
        int[] center = {
            this.x + (this.width / 2),
            this.y + (this.height / 2)
        };
        return center;
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

    public int getImageFrame() {
        return this.imageFrame;
    }

    public void setImageFrame(int imageFrame) {
        this.imageFrame = imageFrame;
    }

    public int getNumOfFrames() {
        return this.numOfFrames;
    }

    public void setNumOfFrames(int numOfFrames) {
        this.numOfFrames = numOfFrames;
    }

    public BufferedImage getSpriteSheet() {
        return this.spriteSheet;
    }

    public void setSpriteSheet(BufferedImage spriteSheet) {
        this.spriteSheet = spriteSheet;
    }

    public BufferedImage[] getSpriteImages() {
        return this.spriteImages;
    }

    public void setSpriteImages(BufferedImage[] spriteImages) {
        this.spriteImages = spriteImages;
    }

    public int getMapEntered() {
        return this.mapEntered;
    }

    public void setMapEntered(int mapEntered) {
        this.mapEntered = mapEntered;
    }

    public int getRoomX() {
        return this.roomX;
    }

    public void setRoomX(int roomX) {
        this.roomX = roomX;
    }

    public int getRoomY() {
        return this.roomY;
    }

    public void setRoomY(int roomY) {
        this.roomY = roomY;
    }

    public String getEntryRequirement() {
        return this.entryRequirement;
    }

    public void setEntryRequirement(String entryRequirement) {
        this.entryRequirement = entryRequirement;
    }

    public boolean isExtendedRequirement() {
        return this.extendedRequirement;
    }

    public boolean getExtendedRequirement() {
        return this.extendedRequirement;
    }

    public void setExtendedRequirement(boolean extendedRequirement) {
        this.extendedRequirement = extendedRequirement;
    }
    //------------------------------------------------------------------------------       
    /**
     * Checks if the player can enter this room based on if the player talked to the right NPC before hand.
     * @param player Player object
     * @return boolean
     */
    public boolean enterRoom(Player player) {
        boolean entryAllowed = false;

        if (this.entryRequirement.equalsIgnoreCase("Betty") && player.getTalkedToBetty()) {
            entryAllowed = true;
        } else if (this.entryRequirement.equalsIgnoreCase("Rupert") && player.getTalkedToRupert()) {
            entryAllowed = true;
        } else if (this.entryRequirement.equalsIgnoreCase("Chad") && player.getTalkedToChad()) {
            entryAllowed = true;
        } else if (this.entryRequirement.equalsIgnoreCase("Brad") && player.getTalkedToBrad()) {
            entryAllowed = true;
        } else if (this.entryRequirement.equalsIgnoreCase("Gus") && player.getTalkedToGus()) {
            entryAllowed = true;
        } else if (this.entryRequirement.equalsIgnoreCase("OutsideGus") && player.getTalkedToOutsideGus()) {
            entryAllowed = true;
        } else if (this.entryRequirement.equalsIgnoreCase("Susan") && player.getTalkedToSusan()) {
            entryAllowed = true;
        } else if (this.entryRequirement.equalsIgnoreCase("MaskedGus") && player.getTalkedToMaskedGus()) {
            entryAllowed = true;
        } else if (this.entryRequirement.equalsIgnoreCase("")) {
            entryAllowed = true;
        }

        return entryAllowed;
    }
    //------------------------------------------------------------------------------            
    public void updateFrame() {
        this.imageFrame = (this.imageFrame + 1) % Const.FPS;
    }
    //------------------------------------------------------------------------------       
    public void draw(Graphics2D g2d, int[] scroll) {
        // draw from the bottom left corner
        g2d.drawImage(this.spriteImages[this.imageFrame / (Const.FPS / this.numOfFrames)], this.x + scroll[0], this.y + Const.TILE_SIZE + scroll[1] - this.height, null);
    }
    //------------------------------------------------------------------------------       
    @Override
    public String toString() {
        return "{" + super.toString() + "\b," +
            " x='" + getX() + "'" +
            ", y='" + getY() + "'" +
            ", width='" + getWidth() + "'" +
            ", height='" + getHeight() + "'" +
            ", imageFrame='" + getImageFrame() + "'" +
            ", numOfFrames='" + getNumOfFrames() + "'" +
            ", spriteSheet='" + getSpriteSheet() + "'" +
            ", spriteImages='" + getSpriteImages() + "'" +
            ", mapEntered='" + getMapEntered() + "'" +
            ", roomX='" + getRoomX() + "'" +
            ", roomY='" + getRoomY() + "'" +
            ", entryRequirement='" + getEntryRequirement() + "'" +
            ", extendedRequirement='" + isExtendedRequirement() + "'" +
            "}";
    }
}