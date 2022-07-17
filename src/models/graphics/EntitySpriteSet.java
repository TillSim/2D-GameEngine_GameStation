package models.graphics;


import utilities.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;



/**
 * EntitySpriteSet is a container, that loads sprites for all directions.
 */
public class EntitySpriteSet {


    private static final String STRIPE_DIR = "res/img/sprites/";

    public final ArrayList<BufferedImage> UP, DOWN, LEFT, RIGHT;
    private final int SPRITE_AMOUNT;
    private final String ENTITY_NAME;


    public EntitySpriteSet(String entity) {
        this.ENTITY_NAME = entity;
        this.SPRITE_AMOUNT = countAvailableSprites();
        this.UP = loadSpriteLib("up");
        this.DOWN = loadSpriteLib("down");
        this.LEFT = loadSpriteLib("left");
        this.RIGHT = loadSpriteLib("right");
    }


    /**
     * loads all available entity sprites from resources
     * @param direction String
     * @return ArrayList(BufferedImage)
     */
    private ArrayList<BufferedImage> loadSpriteLib(String direction) {
        ArrayList<BufferedImage> sprites = new ArrayList<>();

        for (int i = 0; i < SPRITE_AMOUNT; i++) {
            String url = STRIPE_DIR + ENTITY_NAME + "/" + direction + "/" + direction + i + ".png";
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
            case "up" -> UP.get(spriteIndex);
            case "down" -> DOWN.get(spriteIndex);
            case "left" -> LEFT.get(spriteIndex);
            case "right" -> RIGHT.get(spriteIndex);
            default -> null;
        };

    }

    /**
     * counts available sprites in resources
     * @return int
     */
    private int countAvailableSprites() {
        File tileFolder = new File(STRIPE_DIR + ENTITY_NAME + "/up");
        int counter = 0;

        for (File entry : tileFolder.listFiles()) {
            counter++;
        }

        Logger.toFile(counter + " sprites found for ENTITY{" + ENTITY_NAME + "}");
        return counter;

    }


}
