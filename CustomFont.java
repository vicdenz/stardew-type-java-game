import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.awt.FontFormatException;

/**
 * CustomFont class to load .ttf files and create fonts with set size.
 * Taken from Oracle Java Docs. https://docs.oracle.com/javase/tutorial/2d/text/fonts.html
 * @author ICS3U6
 * @version May 2022
 */
public final class CustomFont {
	/**
	 * Returns custom font with set size, otherwise, Arial font with set size
	*/
	public static final Font loadFont(String fontPath, int fontSize) {
		try {
		     //Returned font is of pt size 1
		     Font font = Font.createFont(Font.TRUETYPE_FONT, new File(fontPath));
		
		     //Derive and return a 12 pt version:
		     //Need to use float otherwise
		     //it would be interpreted as style
			return font.deriveFont((float)fontSize);
		} catch (IOException|FontFormatException e) {
		     return new Font("Arial", Font.PLAIN, fontSize);
		}
	}
}