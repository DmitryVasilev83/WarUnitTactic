package com.mygdx.ecs.entities;

import com.mygdx.ecs.components.Component;

import java.util.*;

public class EntityManager {
    private final List<Entity> entities = new ArrayList<>();

    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    public List<Entity> getEntities() {
        return new ArrayList<>(entities);
    }

    @SafeVarargs
    public final List<Entity> getEntitiesWith(Class<? extends Component>... componentClasses) {
        List<Entity> result = new ArrayList<>();
        for (Entity entity : entities) {
            boolean hasAll = true;
            for (Class<? extends Component> compClass : componentClasses) {
                if (!entity.hasComponent(compClass)) {
                    hasAll = false;
                    break;
                }
            }
            if (hasAll) {
                result.add(entity);
            }
        }
        return result;
    }
}
