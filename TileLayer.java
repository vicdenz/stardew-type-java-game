import java.awt.Graphics2D;

import java.util.Scanner;
import java.io.File;

// possible exceptions
import java.io.IOException;

/**
 * The TileLayer class is a complete layer of a map. Its data is parsed from a .txt file and each tile index maps to a index in a seperate TileSet.
 * @see Tile
 * @see TileSet
 * @author David Daniliuc
 * @version "1.8.0_322"
 */
public class TileLayer {
    private int rows;
    private int columns;
    private boolean collision;
    private Tile[][] tiles;
    private TileSet tileset;
    //------------------------------------------------------------------------------       
    TileLayer(TileSet tileset, String layerName, boolean collision) {
        //read the map file to find out the map dimensions
        this.tileset = tileset;
        this.collision = collision;
        try {
            Scanner input = new Scanner(new File(layerName));
            while (input.hasNext()) {
                String[] tile_line = input.nextLine().split(",");
                this.rows++;
                if (tile_line.length > this.columns) {
                    this.columns = tile_line.length;
                }
            }
            input.close();
        } catch (IOException ex) {
            System.out.println("TileLayer: Unable to load map layer file. " + layerName);
        }
        //read and process the map file, adding tiles to the map
        this.tiles = new Tile[this.rows][this.columns];
        try {
            Scanner input = new Scanner(new File(layerName));

            for (int row = 0; row < this.rows; row++) {
                String[] tile_line = input.nextLine().split(",");

                // tile_line.length minus 1 to elimenate trailing ',' in .txt file
                for (int col = 0; col < tile_line.length; col++) {
                    int tileset_index = Integer.parseInt(tile_line[col]);

                    // if index is positive, it is treated as a collision tile
                    // if index is negative, it is treated as a non-collision tile
                    // For a invisible barrier, just select an empty spot in the tile set.
                    if (tileset_index > 0) {
                        this.tiles[row][col] = new Tile(row, col, this.tileset.indexTile(tileset_index), this.collision);
                    }
                }
            }
            input.close();
        } catch (IOException ex) {
            System.out.println("TileLayer: Unable to read map layer tiles. " + layerName);
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

    public boolean isCollision() {
        return this.collision;
    }

    public boolean getCollision() {
        return this.collision;
    }

    public void setCollision(boolean collision) {
        this.collision = collision;
    }

    public Tile[][] getTiles() {
        return this.tiles;
    }

    public void setTiles(Tile[][] tiles) {
        this.tiles = tiles;
    }

    public TileSet getTileset() {
        return this.tileset;
    }

    public void setTileset(TileSet tileset) {
        this.tileset = tileset;
    }
    //------------------------------------------------------------------------------       	
    public void addTile(Tile tile, int row, int column) {
        this.tiles[row][column] = tile;
    }

    public void removeTile(int row, int column) {
        this.tiles[row][column] = null;
    }

    public Tile getTile(int row, int column) {
        return this.tiles[row][column];
    }
    //------------------------------------------------------------------------------       	
    public void draw(Graphics2D g2d) {
        for (int row = 0; row < this.rows; row++) {
            for (int column = 0; column < this.columns; column++) {
                Tile tile = this.tiles[row][column];
                if (tile != null) {
                    tile.draw(g2d);
                }
            }
        }
    }
    //------------------------------------------------------------------------------       	
    public void draw(Graphics2D g2d, int[] scroll) {
        for (int row = 0; row < this.rows; row++) {
            for (int column = 0; column < this.columns; column++) {
                Tile tile = this.tiles[row][column];
                if (tile != null) {
                    tile.draw(g2d, scroll);
                }
            }
        }
    }
    //------------------------------------------------------------------------------       	
    @Override
    public String toString() {
        return "{" +
            " rows='" + getRows() + "'" +
            ", columns='" + getColumns() + "'" +
            ", collision='" + isCollision() + "'" +
            ", tiles='" + getTiles() + "'" +
            ", tileset='" + getTileset() + "'" +
            "}";
    }
}