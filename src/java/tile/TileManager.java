package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class TileManager {
    GamePanel panel;
    public Tile[] tileSet;
    public int[][] currentMapTiles;

    public int currentWorldRows;
    public int currentWorldCols;

    public TileManager(GamePanel panel){
        this.panel = panel;
        currentMapTiles = new int [panel.maxWorldCol+1][panel.maxWorldRow+1];
        tileSet = new Tile[32];

        getTileImage();
        loadMap("/maps/map01.map");
    }
    public void getTileImage() {
        try {
            tileSet[0] = new Tile();
            tileSet[0].sprite = ImageIO.read(getClass().getResourceAsStream("/tiles/grass0.png"));
            tileSet[1] = new Tile();
            tileSet[1].sprite = ImageIO.read(getClass().getResourceAsStream("/tiles/water0.png"));
            tileSet[2] = new Tile();
            tileSet[2].sprite = ImageIO.read(getClass().getResourceAsStream("/tiles/waterGTL0.png"));
            tileSet[2].collision = true;
            tileSet[3] = new Tile();
            tileSet[3].sprite = ImageIO.read(getClass().getResourceAsStream("/tiles/waterGTM0.png"));
            tileSet[3].collision = true;
            tileSet[4] = new Tile();
            tileSet[4].sprite = ImageIO.read(getClass().getResourceAsStream("/tiles/waterGTR0.png"));
            tileSet[4].collision = true;
            tileSet[5] = new Tile();
            tileSet[5].sprite = ImageIO.read(getClass().getResourceAsStream("/tiles/waterGML0.png"));
            tileSet[5].collision = true;
            tileSet[6] = new Tile();
            tileSet[6].sprite = ImageIO.read(getClass().getResourceAsStream("/tiles/waterGMR0.png"));
            tileSet[6].collision = true;
            tileSet[7] = new Tile();
            tileSet[7].sprite = ImageIO.read(getClass().getResourceAsStream("/tiles/waterGBL0.png"));
            tileSet[7].collision = true;
            tileSet[8] = new Tile();
            tileSet[8].sprite = ImageIO.read(getClass().getResourceAsStream("/tiles/waterGBM0.png"));
            tileSet[8].collision = true;
            tileSet[9] = new Tile();
            tileSet[9].sprite = ImageIO.read(getClass().getResourceAsStream("/tiles/waterGBR0.png"));
            tileSet[9].collision = true;
            tileSet[10] = new Tile();
            tileSet[10].sprite = ImageIO.read(getClass().getResourceAsStream("/tiles/waterBLC0.png"));
            tileSet[10].collision = true;
            tileSet[11] = new Tile();
            tileSet[11].sprite = ImageIO.read(getClass().getResourceAsStream("/tiles/waterTLC0.png"));
            tileSet[11].collision = true;
            tileSet[12] = new Tile();
            tileSet[12].sprite = ImageIO.read(getClass().getResourceAsStream("/tiles/waterTRC0.png"));
            tileSet[12].collision = true;
            tileSet[13] = new Tile();
            tileSet[13].sprite = ImageIO.read(getClass().getResourceAsStream("/tiles/waterBRC0.png"));
            tileSet[13].collision = true;
            tileSet[14] = new Tile();
            tileSet[14].sprite = ImageIO.read(getClass().getResourceAsStream("/tiles/path0.png"));
            tileSet[15] = new Tile();
            tileSet[15].sprite = ImageIO.read(getClass().getResourceAsStream("/tiles/smallAppleTree0.png"));
            tileSet[15].collision = true;
        }catch(IOException e){
            e.printStackTrace();
        }}
    public void draw(Graphics2D g2d){
        int worldRow = 0;
        int worldCol = 0;
        while(worldCol < currentWorldRows && worldRow < currentWorldRows){

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
            if (worldCol == currentWorldCols) {
                worldCol = 0;
                worldRow++;
            }
        }
    }
    public void loadMap(String dir) {
        try {
            InputStream is = getClass().getResourceAsStream(dir);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            //int col = 0;
            //int row = 0;

            ArrayList<String[]> tileData = new ArrayList<>();

            br.lines().forEach(line -> tileData.add(line.split(" ")));
            int i = 0;
            int j = 0;
            for(String[] row:tileData) {
                for(String col:row) {
                    int num = Integer.parseInt(col);
                    currentMapTiles[i][j] = num;
                    i++;
                }
                currentWorldCols = i;
                i=0;
                j++;
            }
            currentWorldRows = j;
            /* while(col < panel.maxWorldCol && row < panel.maxWorldRow){
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
             */
        }
        catch(Exception e){
            System.err.println(e.getMessage());
        }
    }
}
