package main;

import entity.Entity;
import entity.Player;
import event.appleTreeInteraction;

public enum Event {
    WALL_COLLISION {
        public void on(EventHandler evH) {}

        public void off(EventHandler evH) {}
    },
    WATER_COLLISION {
        public void on(EventHandler evH, Entity entity) {
            evH.waterCollisionOn(entity);
        }

        public void off(EventHandler evH, Entity entity) {
            evH.waterCollisionOff(entity);
        }
    },
    FIRE_COLLISION {
        public void on(EventHandler evH, Entity entity) {
            evH.fireCollisionOn(entity);
        }

        public void off(EventHandler evH, Entity entity) {
            evH.fireCollisionOff(entity);
        }

    },
    appleTreeInteraction {
        public void on(EventHandler evH,int col, int row, Player player) {
            System.out.println("Executing apple tree interaction!");
            evH.appleTreeInteraction.execute(col,row,player);
        }}
    ;

    public void on(EventHandler evH, Entity entity) {}
    public void on(EventHandler evH) {}
    public void off(EventHandler evH, Entity entity) {}
    public void off(EventHandler evH) {}

    public void on(EventHandler eventHandler, int row, int col, Player player) {}
}

