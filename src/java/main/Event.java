package main;

import entity.Entity;
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
    };
    public void on(EventHandler evH, Entity entity) {}
    public void on(EventHandler evH) {}
    public void off(EventHandler evH, Entity entity) {}
    public void off(EventHandler evH) {}
}

