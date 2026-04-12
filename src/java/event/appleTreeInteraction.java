package event;

import entity.Player;
import main.GamePanel;

public class appleTreeInteraction {
    GamePanel panel;
    public appleTreeInteraction(GamePanel panel) {
        this.panel = panel;
    }
    public void execute(int tileCol, int tileRow, Player player) {
        System.out.println("Player got an apple!");
        player.apples++;
        panel.tm.currentMapTiles[1][tileCol][tileRow] = 16;
        System.out.println(panel.tm.currentMapTiles[1][tileCol][tileRow]);
    }
}
