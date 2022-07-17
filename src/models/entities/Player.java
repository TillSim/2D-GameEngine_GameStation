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

    private final InputHandler inputHandler;


    private Player() {
        super.setX(Core.PANEL_WIDTH/2);
        super.setY(Core.PANEL_HEIGHT/2);
        super.hitbox = new Rectangle(16 , 32 , 32 , 32);
        this.movementSpeed = 2;
        this.direction = "down";
        this.spriteAmount = 9;
        this.spriteIndex = 0;
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
    //TODO implement idle animation
    @Override
    public void update() {
        isColliding = false;

        //continue animation only when moving
        if (inputHandler.pressedUP
                || inputHandler.pressedDOWN
                || inputHandler.pressedLEFT
                || inputHandler.pressedRIGHT) {

            //change moving and animation speed
            if (inputHandler.pressedRUN) {
                movementSpeed = 3;
            } else if (inputHandler.pressedSNEAK) {
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
                    case "up" -> setY(getY() - movementSpeed);
                    case "down" -> setY(getY() + movementSpeed);
                    case "left" -> setX(getX() - movementSpeed);
                    case "right" -> setX(getX() + movementSpeed);
                }
            }

            //loop through animation
            spriteCounter++;
            if (spriteCounter > (spriteAmount - this.animationSpeed) / movementSpeed) {
                spriteIndex++;
                if (spriteIndex >= spriteAmount) {
                    spriteIndex = 1;
                }
                spriteCounter = 0;
            }

        } else {spriteIndex = 0;}

    }

    /**
     * draws the player at map location with calculated animation
     * @param graphics2D Graphics2D
     */
    @Override
    public void draw(Graphics2D graphics2D) {
        if (spriteSet.getSprite(direction, spriteIndex) != null) {
            BufferedImage sprite = spriteSet.getSprite(direction, spriteIndex);
            graphics2D.drawImage(sprite, this.getX(), this.getY(), Core.TILE_SIZE, Core.TILE_SIZE, null);
        } else {Logger.toStream("failed to get sprite from sprite sheet");}
    }


}
