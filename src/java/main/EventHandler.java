package main;

import entity.Entity;
import entity.Player;
import event.appleTreeInteraction;
import object.OBJ_Base;

import java.util.ArrayList;
import java.util.Collections;

public class EventHandler {
    ArrayList<Event> applicableEvents = new ArrayList<>();
    GamePanel panel;
    public appleTreeInteraction appleTreeInteraction;

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
    public boolean hasEvent(Event event) {
        return applicableEvents.contains(event);
    }
    public void executeEntityEvents(Entity entity) {
        for (Event event : entity.events) {
            event.on(this,entity);
        }
    }
    public void executeTileEvents(int layer, int col, int row, Player player) {
        panel.keyH.resetInput();
        if(col >= 0 && row >= 0 && layer >= 0) {
            try {
                for(Event event:panel.tm.tileSet[panel.tm.currentMapTiles[layer][col][row]].events) {
                    event.on(this,col,row,player);
                    }
            }catch(ArrayIndexOutOfBoundsException e) {
            }
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

    private void initEvents() {
        appleTreeInteraction = new appleTreeInteraction(panel);
    }

    // Unimplemented
    public void fireCollisionOn(Entity entity) {}
    public void fireCollisionOff(Entity entity) {}
}
