package event;

import entity.Entity;
import entity.Player;
import main.EventHandler;
import main.GamePanel;

public class AppleTreeInteraction implements TileEvent {
    GamePanel panel;
    public static final String NAME = "AppleTreeInteraction";
    public AppleTreeInteraction(GamePanel panel) {
        this.panel = panel;
    }
    @Override
    public void on(EventHandler evH, int tileCol, int tileRow, Entity entity) {
        System.out.println("Player got an apple!");
        ((Player)entity).apples++;
        panel.tm.currentMapTiles[1][tileCol][tileRow] = 16;
        System.out.println(panel.tm.currentMapTiles[1][tileCol][tileRow]);
    }

    @Override
    public void off(EventHandler evH, int col, int row, Entity entity) {

    }
    public String getName(){
        return NAME;
    }
}
