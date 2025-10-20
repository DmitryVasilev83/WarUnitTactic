package com.mygdx.ecs.systems;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.ecs.components.PositionComponent;
import com.mygdx.ecs.components.RenderComponent;
import com.mygdx.ecs.entities.Entity;
import com.mygdx.ecs.entities.EntityManager;

import java.util.List;

public class RenderSystem {
    private EntityManager entityManager;
    private SpriteBatch batch;

    public RenderSystem(EntityManager entityManager, SpriteBatch batch) {
        this.entityManager = entityManager;
        this.batch = batch;
    }

    public void render() {
        List<Entity> renderable = entityManager.getEntitiesWith(
                PositionComponent.class, RenderComponent.class
        );

        // Сортировка по zOrder
        renderable.sort((a, b) -> {
            int z1 = a.getComponent(RenderComponent.class).zOrder;
            int z2 = b.getComponent(RenderComponent.class).zOrder;
            return Integer.compare(z1, z2);
        });

        for (Entity entity : renderable) {
            PositionComponent pos = entity.getComponent(PositionComponent.class);
            RenderComponent render = entity.getComponent(RenderComponent.class);

            // Используем новый метод отрисовки
            render.render(batch, pos.worldX, pos.worldY);
        }
    }
}
