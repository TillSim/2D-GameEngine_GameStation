package engine;


import models.entities.Entity;



/**
 * CollisionDirector is a helper class for model collision checking.
 */
//TODO add object checker after implementation of game objects
public abstract class CollisionDirector {

    /**
     * checks if the two neighbouring tiles in the current direction are solid and sets the entities state to "isColliding"
     * -> factors in hitbox
     * @param entity Entity
     */
    public static void checkTile(Entity entity) {
        int collisionCandidateA , collisionCandidateB , predictedDistance;

        //calculate boundary (hitbox position on the map)
        int boundaryLeft = entity.mapX + entity.hitbox.x;
        int boundaryRight = entity.mapX + entity.hitbox.x + entity.hitbox.width;
        int boundaryTop = entity.mapY + entity.hitbox.y;
        int boundaryBottom = entity.mapY + entity.hitbox.y + entity.hitbox.height;

        //convert boundary distance to tile -> tiles around hitbox
        int predictedTileLeft = boundaryLeft / Core.TILE_SIZE;
        int predictedTileRight = boundaryRight / Core.TILE_SIZE;
        int predictedTileTop = boundaryTop / Core.TILE_SIZE;
        int predictedTileBottom = boundaryBottom / Core.TILE_SIZE;

        switch (entity.direction) {
            case "up" -> {
                predictedDistance = (boundaryTop - entity.movementSpeed) / Core.TILE_SIZE;
                collisionCandidateA = Core.map.tileMap[predictedTileLeft][predictedDistance];
                collisionCandidateB = Core.map.tileMap[predictedTileRight][predictedDistance];
                if (Core.map.tileLib.get(collisionCandidateA).isSolid || Core.map.tileLib.get(collisionCandidateB).isSolid) {
                    entity.isColliding = true;
                }
            }
            case "down" -> {
                predictedDistance = (boundaryBottom + entity.movementSpeed) / Core.TILE_SIZE;
                collisionCandidateA = Core.map.tileMap[predictedTileLeft][predictedDistance];
                collisionCandidateB = Core.map.tileMap[predictedTileRight][predictedDistance];
                if (Core.map.tileLib.get(collisionCandidateA).isSolid || Core.map.tileLib.get(collisionCandidateB).isSolid) {
                    entity.isColliding = true;
                }
            }
            case "left" -> {
                predictedDistance = (boundaryLeft - entity.movementSpeed) / Core.TILE_SIZE;
                collisionCandidateA = Core.map.tileMap[predictedDistance][predictedTileTop];
                collisionCandidateB = Core.map.tileMap[predictedDistance][predictedTileBottom];
                if (Core.map.tileLib.get(collisionCandidateA).isSolid || Core.map.tileLib.get(collisionCandidateB).isSolid) {
                    entity.isColliding = true;
                }
            }
            case "right" -> {
                predictedDistance = (boundaryRight + entity.movementSpeed) / Core.TILE_SIZE;
                collisionCandidateA = Core.map.tileMap[predictedDistance][predictedTileTop];
                collisionCandidateB = Core.map.tileMap[predictedDistance][predictedTileBottom];
                if (Core.map.tileLib.get(collisionCandidateA).isSolid || Core.map.tileLib.get(collisionCandidateB).isSolid) {
                    entity.isColliding = true;
                }
            }
        }

    }


}
