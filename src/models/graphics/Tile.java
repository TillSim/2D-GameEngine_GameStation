package models.graphics;


import utilities.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;



/**
 * Tile is a container, that loads sprite images.
 */
public class Tile {

    private static final String tileSpriteLib = "res/map/tiles/";
    public BufferedImage spriteImage;
    public boolean isSolid;


    public Tile(String tileSprite) {
        this.spriteImage = loadTile(tileSprite);
        this.isSolid = loadState(tileSprite);
    }


    /**
     * loads single tile sprite from resources
     * @param tileSprite String
     * @return BufferedImage
     */
    private BufferedImage loadTile(String tileSprite) {
        BufferedImage tile;
        try {
            tile = ImageIO.read(new File(tileSpriteLib + tileSprite));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Logger.toFile("TILE{" + tileSprite.substring(0 , tileSprite.length() - 4) + "} loaded");
        return tile;
    }

    /**
     * loads collision state of a single tile
     * @param tileSprite String
     * @return boolean
     */
    private boolean loadState(String tileSprite) {
        return tileSprite.substring(3, 6).equals("_C_");
    }

}
