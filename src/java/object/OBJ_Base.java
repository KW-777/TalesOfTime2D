package object;

import entity.Player;
import event.Event;
import main.EventHandler;
import main.GamePanel;
import main.UtilityTool;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class OBJ_Base {
    public BufferedImage sprite;
    UtilityTool uTool = new UtilityTool();
    public String name;
    public ArrayList<Event> events= new ArrayList<>();
    public int worldX, worldY;

    public void draw(Graphics2D g2d, GamePanel panel){
        int screenX = worldX - panel.player.posX + panel.player.screenX;
        int screenY = worldY - panel.player.posY + panel.player.screenY;

        if (screenX + panel.tileSize > 0 &&
                screenX < panel.screenWidth &&
                screenY + panel.tileSize > 0 &&
                screenY < panel.screenHeight) {
            g2d.drawImage(uTool.scaleImage(sprite,panel.tileSize,panel.tileSize), screenX, screenY, null);
    }}
    public void interact(EventHandler evH,Player player) {}
}
