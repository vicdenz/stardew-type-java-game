import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;

import java.awt.Image;
import java.awt.image.BufferedImage;

import java.io.File;

import java.util.Scanner;
import java.io.FileWriter;

// possible exceptions
import java.io.IOException;
import java.awt.FontFormatException;

public final class Const {
    public static final int WIDTH = 1600;
    public static final int HEIGHT = 1200;
    public static final int TILE_SIZE = 64;
    public static final int SPRITE_HEIGHT = 112;
    public static final int FPS = 60;
    public static final int FRAME_DURATION = 1000 / Const.FPS;

    public static final int OUTSIDE_STEP = 6;
    public static final int INSIDE_STEP = 4;

    public static final double OUTSIDE_ZOOM = 1.2d;
    public static final double INSIDE_ZOOM = 1.5d;

    public static final Color WHITE_COLOR = new Color(247, 242, 237);
    public static final Color RED_COLOR = new Color(255, 28, 28);
    public static final Color ORANGE_COLOR = new Color(255, 140, 59);
    public static final Color YELLOW_COLOR = new Color(255, 230, 64);
    public static final Color LIGHT_GREEN_COLOR = new Color(119, 255, 74);
    public static final Color GREEN_COLOR = new Color(7, 130, 7);
    public static final Color LIGHT_BLUE_COLOR = new Color(64, 217, 247);
    public static final Color BLUE_COLOR = new Color(9, 114, 135);
    public static final Color PINK_COLOR = new Color(255, 133, 224);
    public static final Color VIOLET_COLOR = new Color(252, 66, 255);
    public static final Color PURPLE_COLOR = new Color(141, 52, 250);
    public static final Color LIGHT_BROWN_COLOR = new Color(199, 124, 84);
    public static final Color BROWN_COLOR = new Color(125, 50, 12);
    public static final Color BLACK_COLOR = new Color(15, 15, 15);
    public static final Color CLEAR_COLOR = new Color(0, 0, 0, 0);

    public static final Color OCEAN_COLOR = new Color(80, 167, 232);
    public static final Color ROOM_COLOR = new Color(248, 248, 248);
    public static final Color CAVE_COLOR = new Color(19, 18, 12);

    private static final Font ROOT_FONT = Const.loadFont("global_font.ttf", 12);
    public static final Font SMALL_FONT = ROOT_FONT.deriveFont(34f);
    public static final Font MEDIUM_FONT = ROOT_FONT.deriveFont(52f);
    public static final Font LARGE_FONT = ROOT_FONT.deriveFont(84f);
    public static final Font VERY_LARGE_FONT = ROOT_FONT.deriveFont(100f);
    public static final Font TITLE_FONT = ROOT_FONT.deriveFont(180f);

    public static final Sound MUSIC_SOUND = new Sound("sounds/music.wav");
    public static final Sound CLICK_SOUND = new Sound("sounds/click.wav");
    public static final Sound[] MALE_TALKING_SOUNDS = {
        new Sound("sounds/male_talking_short.wav"),
        new Sound("sounds/male_talking_long.wav")
    };
    public static final Sound[] FEMALE_TALKING_SOUNDS = {
        new Sound("sounds/female_talking_short.wav"),
        new Sound("sounds/female_talking_long.wav")
    };
    public static final Sound END_SOUND = new Sound("sounds/end.wav");

    private Const() {}
    //------------------------------------------------------------------------------       
    /**
     * Loads .ttf files and create fonts with set size.
     * Taken from Oracle Java Docs. https://docs.oracle.com/javase/tutorial/2d/text/fonts.html
     * @author ICS3U6
     * @version May 2022
     */
    public static final Font loadFont(String fontPath, int fontSize) {
        try {
            //Returned font is of pt size 1
            Font font = Font.createFont(Font.TRUETYPE_FONT, new File(fontPath));

            //Derive and return a 12 pt version:
            //Need to use float otherwise
            //it would be interpreted as style
            return font.deriveFont((float) fontSize);
        } catch (IOException | FontFormatException e) {
            return new Font("Arial", Font.PLAIN, fontSize);
        }
    }
    //------------------------------------------------------------------------------       
    /** 
     * Loads a .txt map layer file and substracts every number by a given offset.
     * Used to convert .tmx layer data to custom .txt layer data format.
     * @author David Daniliuc
     * @version June 2022
     */
    public static void convertTileLayer(String layerName, int rows, int columns, int offset) {
        //read a layer file and parse it
        int[][] map = new int[rows][columns];
        try {
            Scanner input = new Scanner(new File(layerName));

            for (int row = 0; row < rows; row++) {
                String[] tile_line = input.nextLine().split(",");

                for (int col = 0; col < tile_line.length; col++) {
                    map[row][col] = Integer.parseInt(tile_line[col]);
                }
            }
            input.close();
        } catch (IOException ex) {
            System.out.println("Const: Unable to load map layer file. " + layerName);
        }

        // rewrite the layer file with the offset substracted
        try {
            FileWriter layerWriter = new FileWriter(new File(layerName));

            for (int row = 0; row < map.length; row++) {
                String lineString = "";

                for (int col = 0; col < map[row].length; col++) {
                    int tileset_index = map[row][col];

                    if (tileset_index == 0) {
                        lineString = lineString + tileset_index + ",";
                    } else {
                        lineString = lineString + (tileset_index - offset) + ",";
                    }
                }
                layerWriter.write(lineString + "\n");
                System.out.println(lineString);
            }
            layerWriter.close();
        } catch (IOException ex) {
            System.out.println("Const: Unable to read map layer tiles. " + layerName);
        }
    }
    //------------------------------------------------------------------------------       
    /** 
     * Takes an image and resizes it to a given width and height using smooth scale.
     * @author David Daniliuc
     * @version June 2022
     */
    public static BufferedImage resizeImage(BufferedImage img, int newWidth, int newHeight) {
        Image tmp = img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }
    //------------------------------------------------------------------------------       
    /** 
     * Takes a sound or an array of sounds, if the sound is still playing. It ends and flushs the sound.
     * @author David Daniliuc
     * @version June 2022
     */
    public static void resetSound(Sound sound) {
        if (sound.isRunning()) {
            sound.stop();
            sound.flush();
        }
    }

    public static void resetSound(Sound[] sounds) {
        for (int soundIndex = 0; soundIndex < sounds.length; soundIndex++) {
            if (sounds[soundIndex].isRunning()) {
                sounds[soundIndex].stop();
                sounds[soundIndex].flush();
            }
        }
    }
}