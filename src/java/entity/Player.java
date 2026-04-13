package entity;

import event.Event;
import event.WallCollisionEvent;
import event.WaterCollisionEvent;
import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import static java.lang.Math.round;

public class Player extends Entity {
    GamePanel panel;
    KeyHandler kH;
    boolean movementKeyPressed = false;
    public int screenX,screenY;
    public int[] facingTiles = new int[4];
    int tilePosX;
    int tilePosY;

    int stamina = 100;
    public int apples;
    public boolean isSprinting = false;

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
        events.add(new WallCollisionEvent());
        events.add(new WaterCollisionEvent());

    }

    public void update() {
        movementHandler();
        checkFacingTiles();
        panel.cHandler.checkObjectCollision(this);
        if(kH.interactPressed) {
            panel.evH.executeTileEvents(1,facingTiles[0],facingTiles[1],this);
            panel.evH.executeTileEvents(1,facingTiles[2],facingTiles[3],this);
        }
        staminaCheck();
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
        movementKeyPressed = kH.upPressed || kH.downPressed || kH.leftPressed || kH.rightPressed;
        isMoving = movementKeyPressed;
        int x = 0,y=0;
        if (movementKeyPressed) {
            if (kH.upPressed) y -=1;
            if (kH.downPressed) y +=1;
            if (kH.leftPressed) x -=1;
            if (kH.rightPressed) x+=1;
        double speed = this.speed;
        if(x !=0 && y !=0) {
            speed = speed / Math.sqrt(2);
        }
        lastPosX = posX;
        lastPosY = posY;
        posX += (int)(x * speed);
        posY += (int)(y * speed);
        if(x<0) direction = "left";
        else if(x>0) direction = "right";
        else if(y<0) direction = "up";
        else if(y>0) direction = "down";
        }

    }
    public void spriteHandler() {
            if (movementKeyPressed) {
                spriteCounter++;
            } else {
                spriteNum = 0;
            }
            if (spriteCounter > (panel.FPS / 30*speed)) {
                spriteCounter = 0;
                if (spriteNum >= down.length - 1) {
                    spriteNum = 0;
                } else {
                    spriteNum++;
                }
            }

    }public void sprintOn() {
        if(!isSprinting) {
            isSprinting = true;
            speed += 3;
        }
    }public void sprintOff() {
        if(isSprinting) {
            speed -= 3;
            isSprinting = false;
        }
    }
    public void staminaCheck() {}
    public void updateCamera() {
        int worldWidth = panel.tm.currentWorldCols * panel.tileSize;
        int worldHeight = panel.tm.currentWorldRows * panel.tileSize;

        screenX = panel.screenWidth/2 - (panel.tileSize/2);
        screenY = panel.screenHeight/2 - (panel.tileSize/2);
        // Lock the screen if near border
        if(posX-panel.tileSize/8 < panel.screenWidth/2) {
            screenX = posX;
        }
        if(posX+panel.tileSize/8 > worldWidth-panel.screenWidth/2) {
            screenX = panel.screenWidth - (worldWidth-posX);
        }if(posY-panel.tileSize/8 < panel.screenHeight/2) {
            screenY = posY;
        }if(posY+panel.tileSize/8 > worldHeight-panel.screenHeight/2) {
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
