package entity;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {
    public int posX, posY;
    public int speed;
    public boolean isMoving;
    public int lastPosX,lastPosY;

    public BufferedImage[] up,down,left,right;
    public String direction;

    public int spriteCounter = 0;
    public int spriteNum = 0;

    public Rectangle collisionBox;
    public boolean collisionOn;

    public void collisionHandler(GamePanel panel) {
        collisionOn = false;
        panel.cHandler.checkTile(this);
        if(collisionOn && isMoving) {
            switch (direction) {
               case "up","down" -> posY = lastPosY;
               case "left","right" -> posX = lastPosX;
            }
        }
    }
}
