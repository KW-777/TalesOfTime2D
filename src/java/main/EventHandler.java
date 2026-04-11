package main;

import entity.Entity;

import javax.xml.stream.events.EndElement;
import java.util.ArrayList;
import java.util.Collections;

public class EventHandler {
    ArrayList<Event> applicableEvents = new ArrayList<Event>();

    public EventHandler() {
    }

    public EventHandler(Event[] events) {
        Collections.addAll(applicableEvents, events);
    }

    public boolean hasEvent(Event event) {
        return applicableEvents.contains(event);
    }
    public void executeEvents(Entity entity) {
        for (Event event : applicableEvents) {
            event.on(this, entity);
        }
    }
    public void removeEvent(Event event, Entity entity) {
        applicableEvents.remove(event);
        event.off(this,entity);
    }
    public void waterCollisionOn(Entity entity) {
        entity.collisionOn = true;
    }
    public void waterCollisionOff(Entity entity) {
        entity.collisionOn = false;
    }


    // Unimplemented
    public void fireCollisionOn(Entity entity) {}
    public void fireCollisionOff(Entity entity) {}
}
