package entity;

import main.Event;
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
        if(collisionOn && isMoving) {
            switch (direction) {
               case "up","down" -> posY = lastPosY;
               case "left","right" -> posX = lastPosX;
            }
        }
    }
}
