package main;

import entity.Entity;
import entity.Player;
import object.OBJ_Base;

public class CollisionHandler {

    GamePanel panel;
    public String collisionSide;

    public CollisionHandler(GamePanel panel) {
        this.panel = panel;
    }
    public void checkTileCollision(Entity entity) {
        entity.collisionOn = false;
        int entityLWX = entity.posX + entity.collisionBox.x;
        int entityRWX = entity.posX + entity.collisionBox.x + entity.collisionBox.width;
        int entityTWY = entity.posY + entity.collisionBox.y;
        int entityBWY = entity.posY + entity.collisionBox.y + entity.collisionBox.height;

        int checkRadius = entity.isMoving ? entity.speed : 0;

        int[][] corners = {
                {entityLWX, entityTWY}, // TL
                {entityRWX, entityTWY}, //TR
                {entityRWX, entityBWY},
                {entityLWX, entityBWY},
        };
        for(int[] corner: corners){
            int col = corner[0]/panel.tileSize;
            int row = corner[1]/panel.tileSize;
            for(int layer = 0;layer<panel.tm.currentWorldLayers;layer++){
                if(panel.tm.isSolid(layer,col,row)) {
                    entity.collisionOn = true;
                    return;
                }
            }
        }

    }
    public void checkObjectCollision(Player player) {
        for(OBJ_Base obj: panel.objects) {
            if(obj != null &&
                    (obj.worldX/panel.tileSize == player.facingTiles[0] || obj.worldX/panel.tileSize == player.facingTiles[2])
                    && (obj.worldY/panel.tileSize == player.facingTiles[1] || obj.worldX/panel.tileSize == player.facingTiles[3]) &&
                    panel.keyH.interactPressed) {
                obj.interact(panel.evH,player);
                }
            }
        }
    }

