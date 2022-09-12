import java.awt.Graphics2D;

import java.io.File;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
// possible exceptions
import java.io.IOException;
import java.awt.image.RasterFormatException;

/**
 * The TileSet class is a parsed image that creates an array where indexes map to tile images.
 * Allowing for maps to be encoded in numbers rather than hard coded objects.
 * @see TileLayer
 * @author David Daniliuc
 * @version "1.8.0_322"
 */
public class TileSet {
    private int rows;
    private int columns;
    private BufferedImage spriteSheet;
    private BufferedImage[] tiles;
    //------------------------------------------------------------------------------    
    TileSet(String spriteSheetName) {
        try {
            this.spriteSheet = ImageIO.read(new File(spriteSheetName));

            this.rows = spriteSheet.getHeight() / Const.TILE_SIZE;
            this.columns = this.spriteSheet.getWidth() / Const.TILE_SIZE;

        } catch (IOException ex) {
            System.out.println("Unable to load tile set image. " + spriteSheetName);
        }

        try {
            this.tiles = new BufferedImage[this.rows * this.columns];
            for (int row = 0; row < this.rows; row++) {
                for (int column = 0; column < this.columns; column++) {
                    this.tiles[row * this.columns + column] = this.spriteSheet.getSubimage(column * Const.TILE_SIZE, row * Const.TILE_SIZE, Const.TILE_SIZE, Const.TILE_SIZE);
                }
            }
        } catch (RasterFormatException ex) {
            System.out.println("Unable to parse sprite sheet image. " + spriteSheetName);
        }
    }
    //------------------------------------------------------------------------------    

    public int getRows() {
        return this.rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColumns() {
        return this.columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public BufferedImage getSpriteSheet() {
        return this.spriteSheet;
    }

    public void setSpriteSheet(BufferedImage spriteSheet) {
        this.spriteSheet = spriteSheet;
    }

    public BufferedImage[] getTiles() {
        return this.tiles;
    }

    public void setTiles(BufferedImage[] tiles) {
        this.tiles = tiles;
    }
    //------------------------------------------------------------------------------    
    public BufferedImage indexTile(int index) {
        return this.tiles[index];
    }
    //------------------------------------------------------------------------------    
    public void draw(Graphics2D g2d) {
        g2d.drawImage(this.spriteSheet, 0, 0, null);
    }

    public void drawSet(Graphics2D g2d) {
        int spacing = 10;

        for (int row = 0; row < this.rows; row++) {
            for (int column = 0; column < this.columns; column++) {
                g2d.drawImage(this.tiles[row * this.columns + column], column * (Const.TILE_SIZE + spacing), row * (Const.TILE_SIZE + spacing), null);
            }
        }
    }
    //------------------------------------------------------------------------------    
    @Override
    public String toString() {
        return "{" +
            " rows='" + getRows() + "'" +
            ", columns='" + getColumns() + "'" +
            ", spriteSheet='" + getSpriteSheet() + "'" +
            ", tiles='" + getTiles() + "'" +
            "}";
    }
}