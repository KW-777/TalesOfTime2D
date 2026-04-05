package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Player extends Entity {
    GamePanel panel;
    KeyHandler kH;
    boolean movementKeyPressed = false;

    public final int screenX,screenY;


    public boolean byBorder = false;

    public Player(GamePanel panel, KeyHandler kH) {
        this.panel = panel;
        this.kH = kH;

        screenX = panel.screenWidth/2 - (panel.tileSize/2);
        screenY = panel.screenHeight/2 - (panel.tileSize/2);

        collisionBox = new Rectangle(16, 12, panel.tileSize-32, panel.tileSize-32);

        setDefaultMovementStats();
        getPlayerImage();
    }

    public void setDefaultMovementStats() {
        posX = 10 * panel.tileSize;
        posY = 10 * panel.tileSize;
        speed = 5 * 60/panel.FPS;
        direction = "down";
    }

    public void update() {
        movementHandler();
        super.collisionHandler(panel);
        spriteHandler();
    }

    public void movementHandler() {
        isMoving = false;
        movementKeyPressed = kH.upPressed || kH.downPressed || kH.leftPressed || kH.rightPressed;
        byBorder = posX < 8* panel.tileSize ||
                posX > panel.tm.currentWorldCols * panel.tileSize - 8 * panel.tileSize
                || posY < 6 * panel.tileSize
                || posY > panel.tm.currentWorldCols * panel.tileSize - 6*panel.tileSize;
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
    public void getPlayerImage() {
        try {
            up = new BufferedImage[]{ImageIO.read(getClass().getResourceAsStream("/player/playerUp_0.png")), ImageIO.read(getClass().getResourceAsStream("/player/playerUp_1.png")), ImageIO.read(getClass().getResourceAsStream("/player/playerUp_2.png"))};
            down = new BufferedImage[]{ImageIO.read(getClass().getResourceAsStream("/player/playerDown_0.png")),ImageIO.read(getClass().getResourceAsStream("/player/playerDown_1.png")),ImageIO.read(getClass().getResourceAsStream("/player/playerDown_2.png"))};
            left = new BufferedImage[]{ImageIO.read(getClass().getResourceAsStream("/player/playerLeft_0.png")),ImageIO.read(getClass().getResourceAsStream("/player/playerLeft_1.png")),ImageIO.read(getClass().getResourceAsStream("/player/playerLeft_2.png"))};
            right = new BufferedImage[]{ImageIO.read(getClass().getResourceAsStream("/player/playerRight_0.png")),ImageIO.read(getClass().getResourceAsStream("/player/playerRight_1.png")),ImageIO.read(getClass().getResourceAsStream("/player/playerRight_2.png"))};

        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public void draw(Graphics2D g2d) {
        BufferedImage playerImage = null;
        switch (direction) {
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
        g2d.drawImage(playerImage, screenX, screenY, panel.tileSize, panel.tileSize, null);
    }
}
