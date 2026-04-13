package event;

import entity.Entity;
import main.EventHandler;

public interface Event {
    public static final String NAME = "BaseEvent";

    String getName();
}


