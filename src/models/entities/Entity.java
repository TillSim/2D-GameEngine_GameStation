package models.entities;


import engine.Core;
import models.graphics.EntitySpriteSet;

import java.awt.*;



/**
 * Entity serves as parent class for all movable objects.
 */
public abstract class Entity {


    private int x , y;
    public int movementSpeed;
    public String direction;
    public Rectangle hitbox;
    public boolean isColliding;

    public EntitySpriteSet spriteSet;
    public int spriteAmount, spriteIndex , animationSpeed;
    public int spriteCounter = 0;


    /**
     * controls HOWTO change entity at runtime
     */
    public abstract void update();

    /**
     * controls HOWTO draw entity on panel
     * @param graphics2D Graphics2D
     */
    public abstract void draw(Graphics2D graphics2D);


    public int getX() {
        return x;
    }

    /**
     * prevents entity from running off frame
     * @param x int
     *
     */
    //TODO remove after implementing camera controller

    public void setX(int x) {
        if (x < 0) {
            this.x = 0;
        } else this.x = Math.min(x, Core.PANEL_WIDTH - Core.TILE_SIZE);
    }

    public int getY() {
        return y;
    }

    /**
     * prevents entity from running off frame
     * @param y int
     */
    //TODO remove after implementing camera controller
    public void setY(int y) {
        if (y < 0) {
            this.y = 0;
        } else this.y = Math.min(y, Core.PANEL_HEIGHT - Core.TILE_SIZE);
    }


}
