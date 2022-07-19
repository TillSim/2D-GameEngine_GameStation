package engine;


import models.entities.Player;
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
    private static final String MAP_FILE = "res/map/island.map";

    public final ArrayList<Tile> TILE_LIB;
    public final int[][] TILE_MAP;
    public final int columnAmount, rowAmount , worldWidth , worldHeight;

    private final Player player = Player.getInstance();


    private MapGenerator() {
        this.TILE_LIB = loadTiles();
        this.columnAmount = countMapColumns();
        this.rowAmount = countMapRows();
        this.TILE_MAP = new int[columnAmount][rowAmount];
        this.worldWidth = columnAmount * Core.TILE_SIZE;
        this.worldHeight = rowAmount * Core.TILE_SIZE;

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

            while (column < columnAmount && row < rowAmount) {
                String line = reader.readLine();

                while (column < columnAmount) {
                    String[] numbers = line.split(",");
                    int num = Integer.parseInt(numbers[column]);
                    TILE_MAP[column][row] = num;
                    column++;
                }
                if (column == columnAmount) {
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
        int worldColumn = 0;
        int worldRow = 0;

        while (worldColumn < columnAmount && worldRow < rowAmount) {
            int tileIndex = TILE_MAP[worldColumn][worldRow];

            //calculate tile's screen position
            int worldX = worldColumn * Core.TILE_SIZE;
            int worldY = worldRow * Core.TILE_SIZE;
            int screenX = worldX - player.mapX + player.screenX;
            int screenY = worldY - player.mapY + player.screenY;

            graphics2D.drawImage(this.TILE_LIB.get(tileIndex).spriteImage, screenX , screenY , Core.TILE_SIZE , Core.TILE_SIZE , null);
            worldColumn++;

            if (worldColumn == columnAmount) {
                worldColumn = 0;
                worldRow++;
            }
        }

    }

    /**
     * counts columns in map file
     * @return int
     */
    private int countMapColumns() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(MAP_FILE));
            String line = reader.readLine();
            String[] columns = line.split(",");
            reader.close();
            return columns.length;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * counts rows in map file
     * @return int
     */
    private int countMapRows() {
        try {
            int lines = 0;
            BufferedReader reader = new BufferedReader(new FileReader(MAP_FILE));
            while (reader.readLine() != null) lines++;
            reader.close();
            return lines;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


}
