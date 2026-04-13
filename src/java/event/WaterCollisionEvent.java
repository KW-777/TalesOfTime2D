package event;

import entity.Entity;
import entity.Player;
import main.EventHandler;

public class WaterCollisionEvent implements TileEvent{
    public static final String NAME = "WaterCollisionEvent";
    @Override
    public void on(EventHandler evH, int tileCol, int tileRow, Entity entity) {
        entity.collisionOn = true;
    }

    @Override
    public void off(EventHandler evH, int tileCol, int tileRow, Entity entity) {
        entity.collisionOn = false;
    }
    public String getName(){
        return NAME;
    }
}
