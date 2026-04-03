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


        int entityLeftCol = entityLWX/panel.tileSize;
        int entityRightCol = entityRWX/panel.tileSize;
        int entityTopRow = entityTWY/panel.tileSize;
        int entityBottomRow = entityBWY/panel.tileSize;

        int tileNum1,tileNum2;

        switch(entity.direction) {
            case "up" -> {
                entityTopRow = (entityTWY - 2+entity.speed) / panel.tileSize;
                tileNum1 = panel.tm.currentMapTiles[entityLeftCol][entityTopRow];
                tileNum2 = panel.tm.currentMapTiles[entityRightCol][entityTopRow];
                if (panel.tm.tileSet[tileNum1].collision || panel.tm.tileSet[tileNum2].collision) {
                    entity.collisionOn = true;
                    collisionSide = "up";
                }
            }case "down" -> {
                entityBottomRow = (entityBWY - 2 +entity.speed) / panel.tileSize;
                tileNum1 = panel.tm.currentMapTiles[entityLeftCol][entityBottomRow];
                tileNum2 = panel.tm.currentMapTiles[entityRightCol][entityBottomRow];
                if (panel.tm.tileSet[tileNum1].collision || panel.tm.tileSet[tileNum2].collision) {
                    entity.collisionOn = true;
                    collisionSide = "down";
                }
            }case "left" -> {
                entityLeftCol = (entityLWX - 2 +entity.speed) / panel.tileSize;
                tileNum1 = panel.tm.currentMapTiles[entityLeftCol][entityTopRow];
                tileNum2 = panel.tm.currentMapTiles[entityLeftCol][entityBottomRow];
                if (panel.tm.tileSet[tileNum1].collision || panel.tm.tileSet[tileNum2].collision) {
                    entity.collisionOn = true;
                    collisionSide = "left";
                }
            }case "right" -> {
                entityRightCol = (entityRWX - 2 +entity.speed) / panel.tileSize;
                tileNum1 = panel.tm.currentMapTiles[entityRightCol][entityTopRow];
                tileNum2 = panel.tm.currentMapTiles[entityRightCol][entityBottomRow];
                if (panel.tm.tileSet[tileNum1].collision || panel.tm.tileSet[tileNum2].collision) {
                    entity.collisionOn = true;
                    collisionSide = "right";
                }
            }
        }
    }
}
