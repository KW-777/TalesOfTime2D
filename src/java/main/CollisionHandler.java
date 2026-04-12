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


        int entityLeftCol = entityLWX / panel.tileSize;
        int entityRightCol = entityRWX / panel.tileSize;
        int entityTopRow = entityTWY / panel.tileSize;
        int entityBottomRow = entityBWY / panel.tileSize;

        int checkRadius = entity.isMoving ? entity.speed :0;

        switch (entity.direction) {
            case "up" -> {
                entityTopRow = (entityTWY - checkRadius) / panel.tileSize;
                for (int col = entityLeftCol; col <= entityRightCol; col++) {
                    for(int layer = 0; layer < panel.tm.currentWorldLayers; layer++) {
                        if(panel.tm.isSolid(layer,col,entityTopRow)) {
                            entity.collisionOn = true;
                            break;
                        }
                    }
                }
            }
            case "down" -> {
                entityBottomRow = (entityBWY + checkRadius) / panel.tileSize;
                for (int col = entityLeftCol; col <= entityRightCol; col++) {
                    for(int layer = 0; layer < panel.tm.currentWorldLayers; layer++) {
                        if (panel.tm.isSolid(layer, col, entityBottomRow)) {
                            entity.collisionOn = true;
                            break;
                        }
                    }
                }
            }
            case "left" -> {
                entityLeftCol = (entityLWX - checkRadius) / panel.tileSize;
                for (int row = entityTopRow; row <= entityBottomRow; row++) {
                    for(int layer = 0; layer < panel.tm.currentWorldLayers; layer++) {
                        if (panel.tm.isSolid(layer, entityLeftCol,row)) {
                            entity.collisionOn = true;
                            break;
                        }
                    }
                }
            }
            case "right" -> {
                entityRightCol = (entityRWX + checkRadius) / panel.tileSize;
                for (int row = entityTopRow; row <= entityBottomRow; row++) {
                    for(int layer = 0; layer < panel.tm.currentWorldLayers; layer++) {
                        if (panel.tm.isSolid(layer,entityRightCol,row)) {
                            entity.collisionOn = true;
                            break;
                        }
                    }
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

