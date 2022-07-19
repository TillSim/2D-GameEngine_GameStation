package engine;


import models.entities.Entity;



/**
 * CollisionDirector is a helper class for model collision checking.
 */
//TODO add object checker after implementation of game objects
public abstract class CollisionDirector {

    public static void checkTile(Entity entity) {
        int collisionCandidateA , collisionCandidateB , predictedDistance;

        int boundaryLeft = entity.mapX + entity.hitbox.x;
        int boundaryRight = entity.mapX + entity.hitbox.x + entity.hitbox.width;
        int boundaryTop = entity.mapY + entity.hitbox.y;
        int boundaryBottom = entity.mapY + entity.hitbox.y + entity.hitbox.height;

        int predictedTileLeft = boundaryLeft / Core.TILE_SIZE;
        int predictedTileRight = boundaryRight / Core.TILE_SIZE;
        int predictedTileTop = boundaryTop / Core.TILE_SIZE;
        int predictedTileBottom = boundaryBottom / Core.TILE_SIZE;

        switch (entity.direction) {
            case "up" -> {
                predictedDistance = (boundaryTop - entity.movementSpeed) / Core.TILE_SIZE;
                collisionCandidateA = Core.map.TILE_MAP[predictedTileLeft][predictedDistance];
                collisionCandidateB = Core.map.TILE_MAP[predictedTileRight][predictedDistance];
                if (Core.map.TILE_LIB.get(collisionCandidateA).isSolid || Core.map.TILE_LIB.get(collisionCandidateB).isSolid) {
                    entity.isColliding = true;
                }
            }
            case "down" -> {
                predictedDistance = (boundaryBottom + entity.movementSpeed) / Core.TILE_SIZE;
                collisionCandidateA = Core.map.TILE_MAP[predictedTileLeft][predictedDistance];
                collisionCandidateB = Core.map.TILE_MAP[predictedTileRight][predictedDistance];
                if (Core.map.TILE_LIB.get(collisionCandidateA).isSolid || Core.map.TILE_LIB.get(collisionCandidateB).isSolid) {
                    entity.isColliding = true;
                }
            }
            case "left" -> {
                predictedDistance = (boundaryLeft - entity.movementSpeed) / Core.TILE_SIZE;
                collisionCandidateA = Core.map.TILE_MAP[predictedDistance][predictedTileTop];
                collisionCandidateB = Core.map.TILE_MAP[predictedDistance][predictedTileBottom];
                if (Core.map.TILE_LIB.get(collisionCandidateA).isSolid || Core.map.TILE_LIB.get(collisionCandidateB).isSolid) {
                    entity.isColliding = true;
                }
            }
            case "right" -> {
                predictedDistance = (boundaryRight + entity.movementSpeed) / Core.TILE_SIZE;
                collisionCandidateA = Core.map.TILE_MAP[predictedDistance][predictedTileTop];
                collisionCandidateB = Core.map.TILE_MAP[predictedDistance][predictedTileBottom];
                if (Core.map.TILE_LIB.get(collisionCandidateA).isSolid || Core.map.TILE_LIB.get(collisionCandidateB).isSolid) {
                    entity.isColliding = true;
                }
            }
        }

    }


}
