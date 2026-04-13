package entity;

import event.Event;
import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

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
    public boolean collisionOn = false;
    public ArrayList<Event> events;
    public void collisionHandler(GamePanel panel) {
        panel.cHandler.checkTileCollision(this);
        if(!isMoving) return;
        int savedY = posY;
        posY = lastPosY;
        panel.cHandler.checkTileCollision(this);
        if(collisionOn) {
            posX = lastPosX;
        }
        posY = savedY;
        int savedX = posX;
        posX = lastPosX;
        panel.cHandler.checkTileCollision(this);
        if(collisionOn) {
            posY = lastPosY;
        }
        posX = savedX;
    }
}
