package main;

import entity.Entity;
import entity.Player;
import event.EntityEvent;
import event.Event;
import event.AppleTreeInteraction;
import event.TileEvent;

import java.util.ArrayList;
import java.util.Collections;

public class EventHandler {
    ArrayList<Event> applicableEvents = new ArrayList<>();
    GamePanel panel;
    public AppleTreeInteraction appleTreeInteraction;

    public EventHandler(GamePanel panel) {
        this.panel = panel;
        initEvents();
    }
    public EventHandler(GamePanel panel,Event[] events) {
        Collections.addAll(applicableEvents, events);
        initEvents();
    }
    public void checkPlayerEvents(Player player) {
        for(int i=0;i<panel.tm.currentWorldLayers;i++) {
            executeTileEvents(i,player.facingTiles[0], player.facingTiles[1],player);
        }
        executeEntityEvents(player);
    }
    public boolean hasEvent(ArrayList<Event> events,String EventName) {
        return events.stream().anyMatch(e -> e.getName().equals(EventName));
    }
    public void executeEntityEvents(Entity entity) {
        for (Event event : entity.events) {
            if(event instanceof EntityEvent) {
                ((EntityEvent)event).on(this, entity);
            }
        }
    }
    public void executeTileEvents(int layer, int col, int row, Player player) {
        panel.keyH.resetInput();
        if(col >= 0 && row >= 0 && layer >= 0) {
            try {
                for(Event event:panel.tm.tileSet[panel.tm.currentMapTiles[layer][col][row]].events) {
                    if(event instanceof TileEvent) {
                        ((TileEvent)event).on(this, col, row, player);
                    }
                }
            }catch(ArrayIndexOutOfBoundsException e) {
            }
        }
    }
    public void removeEntityEvent(EntityEvent event, Entity entity) {
        applicableEvents.remove(event);
        event.off(this,entity);
    }
    private void initEvents() {
        appleTreeInteraction = new AppleTreeInteraction(panel);
    }

    // Unimplemented
    public void fireCollisionOn(Entity entity) {}
    public void fireCollisionOff(Entity entity) {}
}
