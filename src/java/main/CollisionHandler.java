package main;

import entity.Entity;

public class CollisionHandler {

    GamePanel panel;
    public String collisionSide;

    public CollisionHandler(GamePanel panel) {
        this.panel = panel;
    }
    public void checkTile(Entity entity) {
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
                    if(panel.tm.isSolid(col,entityTopRow)) {
                        entity.collisionOn = true;
                        break;
                    }
                }
            }
            case "down" -> {
                entityBottomRow = (entityBWY + checkRadius) / panel.tileSize;
                for (int col = entityLeftCol; col <= entityRightCol; col++) {
                    if (panel.tm.isSolid(col,entityBottomRow)) {
                        entity.collisionOn = true;
                        break;
                    }
                }
            }
            case "left" -> {
                entityLeftCol = (entityLWX - checkRadius) / panel.tileSize;
                for (int row = entityTopRow; row <= entityBottomRow; row++) {
                    if (panel.tm.isSolid(entityLeftCol,row)) {
                        entity.collisionOn = true;
                        break;
                    }
                }
            }
            case "right" -> {
                entityRightCol = (entityRWX + checkRadius) / panel.tileSize;
                for (int row = entityTopRow; row <= entityBottomRow; row++) {
                    if (panel.tm.isSolid(entityRightCol,row)) {
                        entity.collisionOn = true;
                        break;
                    }
                }
            }
        }
    }}
