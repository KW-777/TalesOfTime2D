package tile;

import event.AppleTreeInteraction;
import event.Event;
import event.WallCollisionEvent;
import event.WaterCollisionEvent;
import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

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
            setupTile(0,"grass0",new Event[]{});
            setupTile(1,"water0",new Event[]{new WaterCollisionEvent()});
            setupTile(2,"waterGTL0",new Event[]{new WaterCollisionEvent()});
            setupTile(3,"waterGTM0",new Event[]{new WaterCollisionEvent()});
            setupTile(4,"waterGTR0",new Event[]{new WaterCollisionEvent()});
            setupTile(5,"waterGML0",new Event[]{new WaterCollisionEvent()});
            setupTile(6,"waterGMR0",new Event[]{new WaterCollisionEvent()});
            setupTile(7,"waterGBL0",new Event[]{new WaterCollisionEvent()});
            setupTile(8,"waterGBM0",new Event[]{new WaterCollisionEvent()});
            setupTile(9,"waterGBR0",new Event[]{new WaterCollisionEvent()});
            setupTile(10,"waterBLC0",new Event[]{new WaterCollisionEvent()});
            setupTile(11,"waterTLC0",new Event[]{new WaterCollisionEvent()});
            setupTile(12,"waterTRC0",new Event[]{new WaterCollisionEvent()});
            setupTile(13,"waterBRC0",new Event[]{new WaterCollisionEvent()});
            setupTile(14,"path0",new Event[]{});
            setupTile(15,"/objects/smallAppleTree0",new Event[]{new WallCollisionEvent(),new AppleTreeInteraction(panel)});
            setupTile(16,"smallGreenTree0",new Event[]{new WallCollisionEvent()});
            setupTile(17,"smallBrownTree0",new Event[]{new WallCollisionEvent()});
            setupTile(18,"blueFlower0",new Event[]{});
            setupTile(19,"redFlower0",new Event[]{});
            setupTile(20,"purpleFlower0",new Event[]{});
            setupTile(21,"redMushroom0",new Event[]{});
            setupTile(22,"smallLilypad0",new Event[]{});
            setupTile(23,"smallRock0",new Event[]{new WallCollisionEvent()});
            setupTile(24,"bigRock0",new Event[]{new WallCollisionEvent()});
            setupTile(25,"greyWaterRock0",new Event[]{new WallCollisionEvent()});
            setupTile(26,"BrownWaterRock0",new Event[]{new WallCollisionEvent()});
            setupTile(27,"/objects/woodSign0",new Event[]{new WallCollisionEvent()});
            setupTile(28,"/objects/smallChestClosed0",new Event[]{new WallCollisionEvent()});
            setupTile(29,"/objects/smallChestOpened0",new Event[]{new WallCollisionEvent()});
        }catch(Exception e){
            System.out.println(e.getMessage());
        }}
    public void setupTile(int index,String imagePath,Event[] events){
        UtilityTool uTool = new UtilityTool();
        try{
            tileSet[index] = new Tile();
            if(imagePath.substring(0,1).equals("/")) {
                tileSet[index].sprite = uTool.scaleImage(ImageIO.read(getClass().getResourceAsStream(imagePath + ".png")), panel.tileSize, panel.tileSize);
            }else{
                tileSet[index].sprite = uTool.scaleImage(ImageIO.read(getClass().getResourceAsStream("/tiles/" + imagePath + ".png")), panel.tileSize, panel.tileSize);
            }
            Collections.addAll(tileSet[index].events, events);

        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public void draw(Graphics2D g2d) {
        int worldRow = 0;
        int worldCol = 0;
        int tileNum;
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
            return (panel.evH.hasEvent(tileSet[currentMapTiles[layer][col][row]].events,WallCollisionEvent.NAME) || (panel.evH.hasEvent(tileSet[currentMapTiles[layer][col][row]].events,WaterCollisionEvent.NAME) && panel.evH.hasEvent(panel.player.events,WaterCollisionEvent.NAME)));
        }
    }
}
