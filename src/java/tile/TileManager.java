package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {
    GamePanel panel;
    public Tile[] tileSet;
    public int[][] currentMapTiles;
    public TileManager(GamePanel panel){
        this.panel = panel;
        currentMapTiles = new int [panel.maxWorldCol+1][panel.maxWorldRow+1];
        tileSet = new Tile[16];

        getTileImage();
        loadMap("/maps/map01.map");
    }
    public void getTileImage() {
        try {
            tileSet[0] = new Tile();
            tileSet[0].sprite = ImageIO.read(getClass().getResourceAsStream("/tiles/grass0.png"));
            tileSet[1] = new Tile();
            tileSet[1].sprite = ImageIO.read(getClass().getResourceAsStream("/tiles/grass1.png"));
            tileSet[2] = new Tile();
            tileSet[2].sprite = ImageIO.read(getClass().getResourceAsStream("/tiles/water0.png"));
            tileSet[2].collision = true;
        }catch(IOException e){
            e.printStackTrace();
        }}
    public void draw(Graphics2D g2d){
        int worldRow = 0;
        int worldCol = 0;
        while(worldCol < panel.maxWorldCol&&worldRow < panel.maxWorldRow){

            int tileNum = currentMapTiles[worldCol][worldRow];

            int worldX = worldCol * panel.tileSize;
            int worldY = worldRow * panel.tileSize;
            int screenX = worldX - panel.player.posX + panel.player.screenX;
            int screenY = worldY - panel.player.posY + panel.player.screenY;

            if(worldX+panel.tileSize > panel.player.posX - panel.player.screenX &&
                    worldX - panel.tileSize < panel.player.posX + panel.player.screenX &&
                    worldY+ panel.tileSize > panel.player.posY - panel.player.screenY &&
                    worldY-panel.tileSize < panel.player.posY + panel.player.screenY){
                g2d.drawImage(tileSet[tileNum].sprite, screenX, screenY, panel.tileSize, panel.tileSize, null);
            }
            worldCol++;
            if (worldCol == panel.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }
    }
    public void loadMap(String dir) {
        try {
            InputStream is = getClass().getResourceAsStream(dir);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while(col < panel.maxWorldCol && row < panel.maxWorldRow){
               String line = br.readLine();
               while(col < panel.maxWorldCol) {
                   String[] numbers = line.split(" ");
                   int num = Integer.parseInt(numbers[col]);
                   currentMapTiles[col][row] = num;
                   col++;
               }
               if(col == panel.maxWorldCol) {
                   col = 0;
                   row++;
               }
            }
            br.close();
        }
        catch(Exception e){
            System.err.println(e.getMessage());
        }
    }
}
