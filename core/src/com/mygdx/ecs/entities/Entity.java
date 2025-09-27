package com.mygdx.ecs.entities;

import com.mygdx.ecs.components.Component;

import java.util.HashMap;
import java.util.Map;

public class Entity {
    private static int nextId = 0;
    public final int id;
    private final Map<Class<? extends Component>, Component> components;

    public Entity() {
        this.id = nextId++;
        this.components = new HashMap<>();
    }

    public <T extends Component> void addComponent(T component) {
        component.entity = this;
        components.put(component.getClass(), component);
    }

    @SuppressWarnings("unchecked")
    public <T extends Component> T getComponent(Class<T> componentClass) {
        return (T) components.get(componentClass);
    }

    public boolean hasComponent(Class<? extends Component> componentClass) {
        return components.containsKey(componentClass);
    }
}
