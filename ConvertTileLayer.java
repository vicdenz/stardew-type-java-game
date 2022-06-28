import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;

// possible exceptions
import java.io.IOException;

public final class ConvertTileLayer {
    private ConvertTileLayer() {

    }
//------------------------------------------------------------------------------
    public static void convertTileLayer(String layerName, int rows, int columns, int offset){
        //read a layer file and parse it
        int[][] map = new int[rows][columns];
        try {
            Scanner input = new Scanner(new File(layerName));

            for (int row=0; row<rows; row++){
                String[] tile_line = input.nextLine().split(",");

                for (int col=0; col<tile_line.length; col++){
					map[row][col] = Integer.parseInt(tile_line[col]);
				}
            }
            input.close();
        } catch (IOException ex){
			System.out.println("ConvertTileLayer: Unable to load map layer file. " + layerName);
		}  

        // rewrite the layer file with the offset substracted
        try {
            FileWriter layerWriter = new FileWriter(new File(layerName));

            for (int row=0;row<map.length;row++){
                String lineString = "";

                for (int col=0;col<map[row].length;col++){
					int tileset_index = map[row][col];

                    if (tileset_index == 0) {
                        lineString = lineString + tileset_index+",";
                    } else {
                        lineString = lineString + (tileset_index-offset)+",";
                    }
                }
                layerWriter.write(lineString+"\n");
                System.out.println(lineString);
            }
            layerWriter.close();
        } catch (IOException ex){
			System.out.println("ConvertTileLayer: Unable to read map layer tiles. " + layerName);
		}
    }
}