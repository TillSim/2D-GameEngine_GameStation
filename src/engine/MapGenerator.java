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

    public final ArrayList<Tile> tileLib;
    public final int[][] tileMap;
    public final int columnAmount, rowAmount;

    private final Player player = Player.getInstance();


    private MapGenerator() {
        this.tileLib = loadTiles();
        this.columnAmount = countMapColumns();
        this.rowAmount = countMapRows();
        this.tileMap = new int[columnAmount][rowAmount];

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

        //get all available tile files in tile directory
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
                    tileMap[column][row] = num;
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
        int column = 0;
        int row = 0;

        while (column < columnAmount && row < rowAmount) {
            int tileIndex = tileMap[column][row];

            //calculate tile's screen position
            int mapX = column * Core.TILE_SIZE;
            int mapY = row * Core.TILE_SIZE;
            int screenX = mapX - player.mapX + player.screenX;
            int screenY = mapY - player.mapY + player.screenY;

            //only draw tiles on screen position + 1 tile buffer
            if (mapX + Core.TILE_SIZE > player.mapX - player.screenX &&
                    mapX - Core.TILE_SIZE < player.mapX + player.screenX &&
                    mapY + Core.TILE_SIZE > player.mapY - player.screenY &&
                    mapY - Core.TILE_SIZE < player.mapY + player.screenY) {
                graphics2D.drawImage(this.tileLib.get(tileIndex).spriteImage, screenX , screenY , Core.TILE_SIZE , Core.TILE_SIZE , null);
            }

            column++;

            if (column == columnAmount) {
                column = 0;
                row++;
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
