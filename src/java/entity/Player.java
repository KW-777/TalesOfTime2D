package entity;

import main.GamePanel;
import main.KeyHandler;

import java.awt.*;
import java.util.Random;

public class Player extends Entity {
    GamePanel panel;
    KeyHandler kH;
    public Player(GamePanel panel, KeyHandler kH) {
        this.panel = panel;
        this.kH = kH;
        setDefaultMovementStats();
    }
    public void setDefaultMovementStats() {
        Random r = new Random();
        posX = r.nextInt(0,300);
        posY = 100;
        speedX = 3;
        speedY = 3;
    }
    public void update() {
        movementHandler();
    }
    public void movementHandler() {
        if(kH.upPressed) {
            posY -= speedY;
        }else if(kH.downPressed) {
            posY += speedY;
        }else if(kH.leftPressed) {
            posX -= speedX;
        }else if(kH.rightPressed) {
            posX += speedX;
        }
    }
    public void draw(Graphics2D g2d) {
        g2d.setColor(Color.black);
        g2d.fillRect(posX, posY, panel.tileSize, panel.tileSize);
    }
}
