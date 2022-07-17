package engine;


import utilities.Logger;
import models.graphics.Tile;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;



/**
 * The map generator loads the tile sprites and map file from the resources and builds the map.
 */
//TODO add user notifications for missing sprites
public class MapGenerator {


    private static final MapGenerator SINGLETON = new MapGenerator();

    private static final String LIB_PATH = "res/map/tiles";
    private static final String MAP_FILE = "res/map/001.map";

    public final ArrayList<Tile> TILE_LIB;
    public final int[][] TILE_MAP;


    private MapGenerator() {
        this.TILE_LIB = loadTiles();
        this.TILE_MAP = new int[Core.PANEL_COLUMNS][Core.PANEL_ROWS];

        loadMap();
    }

    public static MapGenerator getInstance() {
        return SINGLETON;
    }


    /**
     * loads all available tile sprites from resources
     * @return ArrayList(Tile)
     */
    private ArrayList<Tile> loadTiles() {
        ArrayList<Tile> tiles = new ArrayList<>();
        File tileFolder = new File(LIB_PATH);

        //get all available file paths
        if (tileFolder.listFiles() != null) {
            for (File entry : tileFolder.listFiles()) {
                tiles.add(new Tile(entry.getName()));
            }
            Logger.toFile(tiles.size() + " tile sprites loaded successfully");
            return tiles;
        } else {
            Logger.toFile("failed to load tile sprites");
            return null;
        }

    }

    /**
     * loads the map file from resources
     */
    private void loadMap() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(MAP_FILE));
            int column = 0;
            int row = 0;

            while (column < Core.PANEL_COLUMNS && row < Core.PANEL_ROWS) {
                String line = reader.readLine();

                while (column < Core.PANEL_COLUMNS) {
                    String[] numbers = line.split(",");
                    int num = Integer.parseInt(numbers[column]);
                    TILE_MAP[column][row] = num;
                    column++;
                }
                if (column == Core.PANEL_COLUMNS) {
                    column = 0;
                    row++;
                }
            }
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * draws map from left to right, row after row
     * @param graphics2D Graphics2D
     */
    public void draw(Graphics2D graphics2D) {
        int column = 0;
        int row = 0;
        int x = 0;
        int y = 0;

        while (column < Core.PANEL_COLUMNS && row < Core.PANEL_ROWS) {
            int tileIndex = TILE_MAP[column][row];
            graphics2D.drawImage(this.TILE_LIB.get(tileIndex).spriteImage, x , y , Core.TILE_SIZE , Core.TILE_SIZE , null);
            column++;
            x += Core.TILE_SIZE;

            if (column == Core.PANEL_COLUMNS) {
                column = 0;
                x = 0;
                row++;
                y += Core.TILE_SIZE;
            }
        }

    }


}
