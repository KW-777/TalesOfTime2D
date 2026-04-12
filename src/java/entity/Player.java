package entity;

import main.Event;
import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;
import tile.Tile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

import static java.lang.Math.floor;
import static java.lang.Math.round;

public class Player extends Entity {
    GamePanel panel;
    KeyHandler kH;
    boolean movementKeyPressed = false;
    public int apples = 0;
    public int screenX,screenY;
    public int[] facingTiles = new int[4];
    int tilePosX;
    int tilePosY;
    public Player(GamePanel panel, KeyHandler kH) {
        this.panel = panel;
        this.kH = kH;

        collisionBox = new Rectangle(24, 28, panel.tileSize-42, panel.tileSize-48);
        events = new ArrayList<>();
        setDefaultMovementStats();
        getPlayerImage();
    }

    public void setDefaultMovementStats() {
        posX = 10 * panel.tileSize;
        posY = 10 * panel.tileSize;
        speed = 5 * 60/panel.FPS;
        direction = "down";
        //Default Collisions
        events.add(Event.WALL_COLLISION);
        events.add(Event.WATER_COLLISION);

    }

    public void update() {
        movementHandler();
        checkFacingTiles();
        panel.cHandler.checkObjectCollision(this);
        if(kH.interactPressed) {
            panel.evH.executeTileEvents(1,facingTiles[0],facingTiles[1],this);
            panel.evH.executeTileEvents(1,facingTiles[2],facingTiles[3],this);
        }
        super.collisionHandler(panel);
        spriteHandler();
        updateCamera();
    }

    public void checkFacingTiles() {
        tilePosX = (posX)/panel.tileSize;
        tilePosY = (posY)/ panel.tileSize;
        int offsetX = posX%panel.tileSize;
        int offsetY = posY%panel.tileSize;
        switch(direction) {
            case "up","down" -> {
                int dir = direction.equals("up") ? 0:1;
                int side =(offsetX > panel.tileSize / 2) ? 1:-1;
                facingTiles[0] = tilePosX;
                facingTiles[1] = tilePosY + dir;
                facingTiles[2] = tilePosX + side;
                facingTiles[3] = tilePosY + dir;
            }
            case "left","right"-> {
                int dir = direction.equals("left") ? -1 : 1;
                int side = (offsetY > panel.tileSize / 2) ? 1 : -1;
                facingTiles[0] = tilePosX + dir;
                facingTiles[1] = tilePosY;
                facingTiles[2] = tilePosX + dir;
                facingTiles[3] = tilePosY + side;
            }
        }
    }
    public void movementHandler() {
        isMoving = false;
        movementKeyPressed = kH.upPressed || kH.downPressed || kH.leftPressed || kH.rightPressed;
            if (kH.upPressed) {
                lastPosY = posY;
                direction = "up";
                posY -= speed;

            } else if (kH.downPressed) {
                lastPosY = posY;
                direction = "down";
                posY += speed;
            } else if (kH.leftPressed) {
                lastPosX = posX;
                direction = "left";
                posX -= speed;
            } else if (kH.rightPressed) {
                lastPosX = posX;
                direction = "right";
                posX += speed;
            }
            isMoving = true;
    }
    public void spriteHandler() {
            if (movementKeyPressed) {
                spriteCounter++;
            } else {
                spriteNum = 0;
            }
            if (spriteCounter > (panel.FPS / 30) * 8) {
                spriteCounter = 0;
                if (spriteNum >= down.length - 1) {
                    spriteNum = 0;
                } else {
                    spriteNum++;
                }
            }
    }
    public void updateCamera() {
        int worldWidth = panel.tm.currentWorldCols * panel.tileSize;
        int worldHeight = panel.tm.currentWorldRows * panel.tileSize;

        screenX = panel.screenWidth/2 - (panel.tileSize/2);
        screenY = panel.screenHeight/2 - (panel.tileSize/2);
        // Lock the screen if near border
        if(posX < panel.screenWidth/2) {
            screenX = posX;
        }
        if(posX > worldWidth-panel.screenWidth/2) {
            screenX = panel.screenWidth - (worldWidth-posX);
        }if(posY < panel.screenHeight/2) {
            screenY = posY;
        }if(posY > worldHeight-panel.screenHeight/2) {
            screenY = panel.screenHeight - (worldHeight-posY);
        }
    }
    public void getPlayerImage() {
        try {
            up = setupSprites(("playerUp_0,playerUp_1,playerUp_2").split(","));
            down = setupSprites("playerDown_0,playerDown_1,playerDown_2".split(","));
            left = setupSprites("playerLeft_0,playerLeft_1,playerLeft_2".split(","));
            right = setupSprites("playerRight_0,playerRight_1,playerRight_2".split(","));
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public BufferedImage[] setupSprites(String[] imageNames) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage[] scaledImages = new BufferedImage[imageNames.length];
        try {
            int i = 0;
            for(String dir:imageNames) {
                scaledImages[i] = uTool.scaleImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/" + dir.trim() + ".png"))),panel.tileSize,panel.tileSize);
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return scaledImages;
    }
    public void draw(Graphics2D g2d) {
        BufferedImage playerImage = null;
        switch (direction) {
            // Choose Directional Sprite
            case "up":
                playerImage = up[spriteNum];
                break;
            case "down":
                playerImage = down[spriteNum];
                break;
            case "left":
                playerImage = left[spriteNum];
                break;
            case "right":
                playerImage = right[spriteNum];
                break;

        }
        g2d.drawImage(playerImage, screenX, screenY,null);
    }
}
