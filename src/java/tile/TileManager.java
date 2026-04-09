package tile;

import jdk.jshell.execution.Util;
import main.GamePanel;
import main.UtilityTool;

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
    public int[][][] currentMapTiles;

    public int currentWorldRows;
    public int currentWorldCols;
    public int currentWorldLayers;

    public TileManager(GamePanel panel){
        this.panel = panel;
        currentMapTiles = new int [8][panel.maxWorldCol+1][panel.maxWorldRow+1];
        tileSet = new Tile[32];

        getTileImage();
        loadMap("/maps/mapBase01.map");
        loadMap("/maps/mapNature01.map");
    }
    public void getTileImage() {
        try {
            setupTile(0,"grass0",false);
            setupTile(1,"water0",true);
            setupTile(2,"waterGTL0",true);
            setupTile(3,"waterGTM0",true);
            setupTile(4,"waterGTR0",true);
            setupTile(5,"waterGML0",true);
            setupTile(6,"waterGMR0",true);
            setupTile(7,"waterGBL0",true);
            setupTile(8,"waterGBM0",true);
            setupTile(9,"waterGBR0",true);
            setupTile(10,"waterBLC0",true);
            setupTile(11,"waterTLC0",true);
            setupTile(12,"waterTRC0",true);
            setupTile(13,"waterBRC0",true);
            setupTile(14,"path0",false);
            setupTile(15,"smallAppleTree0",true);
            setupTile(16,"smallGreenTree0",true);
            setupTile(17,"smallBrownTree0",true);
            setupTile(18,"blueFlower0",false);
            setupTile(19,"redFlower0",false);
            setupTile(20,"purpleFlower0",false);
            setupTile(21,"redMushroom0",false);
            setupTile(22,"smallLilypad0",false);
            setupTile(23,"smallRock0",true);
            setupTile(24,"bigRock0",true);
            setupTile(25,"greyWaterRock0",true);
            setupTile(26,"brownWaterRock0",true);
            setupTile(27,"woodSign0",true);
            setupTile(28,"smallChestClosed0",true);
            setupTile(29,"smallChestOpened0",true);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }}
    public void setupTile(int index,String imagePath,boolean collision){
        UtilityTool uTool = new UtilityTool();
        try{
            tileSet[index] = new Tile();
            tileSet[index].sprite = uTool.scaleImage(ImageIO.read(getClass().getResourceAsStream("/tiles/"+ imagePath + ".png")),panel.tileSize,panel.tileSize);
            tileSet[index].collision = collision;
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public void draw(Graphics2D g2d) {
        int worldRow = 0;
        int worldCol = 0;
        int tileNum = 0;
        while (worldCol < currentWorldCols && worldRow < currentWorldRows) {
            try {

                int worldX = worldCol * panel.tileSize;
                int worldY = worldRow * panel.tileSize;
                int screenX = worldX - panel.player.posX + panel.player.screenX;
                int screenY = worldY - panel.player.posY + panel.player.screenY;

                if (screenX + panel.tileSize > 0 &&
                        screenX < panel.screenWidth &&
                        screenY + panel.tileSize > 0 &&
                        screenY < panel.screenHeight) {
                    for (int i = 0; i < currentWorldLayers; i++) {
                        tileNum = currentMapTiles[i][worldCol][worldRow];
                        if (tileNum >= 0) {
                            g2d.drawImage(tileSet[tileNum].sprite, screenX, screenY, null);
                        }
                    }
                }
                worldCol++;
                if (worldCol == currentWorldCols) {
                    worldCol = 0;
                    worldRow++;
                }
            } catch (Exception e) {
                System.err.println("Error while drawing map: " + e.getMessage());
            }
        }
    }
    public void loadMap(String dir) {
        currentWorldLayers++;
        try {
            InputStream is = getClass().getResourceAsStream(dir);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            ArrayList<String[]> tileData = new ArrayList<>();

            br.lines().forEach(line -> tileData.add(line.split(" ")));
            int i = 0;
            int j = 0;
            for(String[] row:tileData) {
                for(String col:row) {
                    if(col.equals(".")){ // Check for blanks from tile editor
                        col = "-1";
                    }
                    int num = Integer.parseInt(col);
                    currentMapTiles[currentWorldLayers-1][i][j] = num;
                    i++;
                }
                currentWorldCols = i;
                i=0;
                j++;
            }
            currentWorldRows = j;
        }
        catch(Exception e){
            System.err.println(e.getMessage());
        }
    }
    public boolean isSolid(int layer,int col, int row) {
        if (col < 0 || row < 0 || col >= currentWorldCols || row >= currentWorldRows)
            return true; // treat out-of-bounds as solid
        if(currentMapTiles[layer][col][row]<0) {return false;}
        else {
            return tileSet[currentMapTiles[layer][col][row]].collision;
        }
    }
}
