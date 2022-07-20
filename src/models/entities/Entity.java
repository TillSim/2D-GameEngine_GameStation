package models.entities;


import models.graphics.EntitySpriteSet;

import java.awt.*;



/**
 * Entity serves as parent class for all movable objects.
 */
public abstract class Entity {


    public int mapX, mapY;
    public int movementSpeed;
    public String direction;
    public Rectangle hitbox;
    public boolean isColliding;

    public EntitySpriteSet spriteSet;
    public int spriteAmount, currentSprite, animationSpeed;
    public int spriteCounter = 0;


    /**
     * controls change of entity atributes at runtime
     */
    public abstract void update();

    /**
     * controls drawing of entity on panel
     * @param graphics2D Graphics2D
     */
    public abstract void draw(Graphics2D graphics2D);


}
