package object;

import entity.Entity;
import entity.Player;
import main.EventHandler;

import javax.imageio.ImageIO;
import java.io.IOException;

public class obj_Chest extends OBJ_Base{
    public obj_Chest() {
        name = "Chest";
        try {
            sprite = ImageIO.read(getClass().getResourceAsStream("/objects/smallChestClosed0.png"));
        }catch(IOException e){
        }
    }
    public void interact(EventHandler evH, Player player) {
        try{
            sprite = ImageIO.read(getClass().getResourceAsStream("/objects/smallChestOpened0.png"));
        }catch(IOException e) {}
    }
}
