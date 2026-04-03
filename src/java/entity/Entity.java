package entity;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {
    public int posX, posY;
    public int speed;

    public BufferedImage[] up,down,left,right;
    public String direction;

    public int spriteCounter = 0;
    public int spriteNum = 0;

    public Rectangle collisionBox;
    public boolean collisionOn;
}
