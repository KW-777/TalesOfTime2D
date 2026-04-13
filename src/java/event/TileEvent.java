package event;

import entity.Entity;
import entity.Player;
import main.EventHandler;

public interface TileEvent extends Event{

    void on(EventHandler evH, int tileCol, int tileRow, Entity entity);

    void off(EventHandler evH, int tileCol, int tileRow, Entity entity);
}
