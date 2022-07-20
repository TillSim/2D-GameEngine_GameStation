package models.entities;


import engine.*;
import utilities.Logger;
import models.graphics.EntitySpriteSet;

import java.awt.*;
import java.awt.image.BufferedImage;



/**
 * Player is the controlable entity.
 */
public class Player extends Entity {

    private static final Player SINGLETON = new Player();

    public final int screenX , screenY;

    private final InputHandler inputHandler;


    private Player() {
        super.mapX = Core.PLAYER_START_TILE_X;
        super.mapY = Core.PLAYER_START_TILE_Y;
        this.screenX = (Core.PANEL_WIDTH / 2) - (Core.TILE_SIZE / 2);
        this.screenY = (Core.PANEL_HEIGHT / 2) - (Core.TILE_SIZE / 2);

        super.hitbox = new Rectangle(16 , 32 , 32 , 32);
        this.movementSpeed = 2;

        this.direction = "down";
        this.spriteAmount = 9;
        this.currentSprite = 0;
        this.animationSpeed = 1;
        this.spriteSet = new EntitySpriteSet("player");

        this.inputHandler = InputHandler.getInstance();
    }

    public static Player getInstance() {
        return SINGLETON;
    }


    /**
     * calculates player movement and animation
     */
    //TODO implement idle animation + add running sprites
    @Override
    public void update() {
        //assume player is not colliding
        isColliding = false;

        //continue animation only when moving
        if (inputHandler.pressedUP
                || inputHandler.pressedDOWN
                || inputHandler.pressedLEFT
                || inputHandler.pressedRIGHT) {

            //change moving and animation speed
            if (inputHandler.pressedSHIFT) {
                movementSpeed = 3;
            } else if (inputHandler.pressedCTRL) {
                movementSpeed = 1;
            } else {
                movementSpeed = 2;
            }


            //get direction
            if (inputHandler.pressedUP) {
                direction = "up";
            } else if (inputHandler.pressedDOWN) {
                direction = "down";
            } else if (inputHandler.pressedLEFT) {
                direction = "left";
            } else if (inputHandler.pressedRIGHT) {
                direction = "right";
            }

            //check if upcoming tiles are solid
            CollisionDirector.checkTile(this);

            //allow movement when upcoming tiles are NOT solid
            if (!isColliding) {
                switch (direction) {
                    case "up" -> mapY -= movementSpeed;
                    case "down" -> mapY += movementSpeed;
                    case "left" -> mapX -= movementSpeed;
                    case "right" -> mapX += movementSpeed;
                }
            }

            //loop through animation
            spriteCounter++;
            if (spriteCounter > (spriteAmount - this.animationSpeed) / movementSpeed) {
                currentSprite++;
                if (currentSprite >= spriteAmount) {
                    currentSprite = 1;
                }
                spriteCounter = 0;
            }

        } else currentSprite = 0;

    }

    /**
     * draws the player at map location with calculated animation
     * @param graphics2D Graphics2D
     */
    @Override
    public void draw(Graphics2D graphics2D) {
        if (spriteSet.getSprite(direction, currentSprite) != null) {
            BufferedImage sprite = spriteSet.getSprite(direction, currentSprite);
            graphics2D.drawImage(sprite, this.screenX, this.screenY, Core.TILE_SIZE, Core.TILE_SIZE, null);
        } else {Logger.toStream("failed to get sprite from sprite sheet");}
    }


}
