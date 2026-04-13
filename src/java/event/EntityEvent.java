package event;

import entity.Entity;
import main.EventHandler;

public interface EntityEvent extends Event {
    void on(EventHandler evH,Entity entity);
    void off(EventHandler evH, Entity entity);
}
