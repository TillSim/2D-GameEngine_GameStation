package models.graphics;


import utilities.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;



/**
 * EntitySpriteSet is a container, that loads sprites for each direction.
 */
public class EntitySpriteSet {


    private static final String SPRITESET_PATH = "res/img/sprites/";

    public final ArrayList<BufferedImage> up, down, left, right;
    private final int spriteAmount;
    private final String entityName;


    public EntitySpriteSet(String entity) {
        this.entityName = entity;
        this.spriteAmount = countAvailableSprites();
        this.up = loadSpriteLib("up");
        this.down = loadSpriteLib("down");
        this.left = loadSpriteLib("left");
        this.right = loadSpriteLib("right");
    }


    /**
     * loads all available entity sprites from resources
     * @param direction String
     * @return ArrayList(BufferedImage)
     */
    private ArrayList<BufferedImage> loadSpriteLib(String direction) {
        ArrayList<BufferedImage> sprites = new ArrayList<>();

        for (int i = 0; i < spriteAmount; i++) {
            String url = SPRITESET_PATH + entityName + "/" + direction + "/" + direction + i + ".png";
            Logger.toFile("SPRITE{" + url + "} loaded");
            try {
                sprites.add(ImageIO.read(new File(url)));
            } catch (IOException e) {
                Logger.toFile("|IOException| failed to load SPRITE{" + url + "}");
                throw new RuntimeException(e);
            }
        }

        Logger.toFile("all {" + direction + "}SPRITES loaded successfully");
        return sprites;

    }

    /**
     * returns entity sprite from sprite library
     * @param direction String
     * @param spriteIndex int
     * @return BufferedImage
     */
    public BufferedImage getSprite(String direction, int spriteIndex) {

        return switch (direction) {
            case "up" -> up.get(spriteIndex);
            case "down" -> down.get(spriteIndex);
            case "left" -> left.get(spriteIndex);
            case "right" -> right.get(spriteIndex);
            default -> null;
        };

    }

    /**
     * counts available sprites in resources
     * @return int
     */
    private int countAvailableSprites() {
        File tileFolder = new File(SPRITESET_PATH + entityName + "/up");
        int counter = 0;

        for (File entry : tileFolder.listFiles()) {
            counter++;
        }

        Logger.toFile(counter + " sprites found for ENTITY{" + entityName + "}");
        return counter;

    }


}
