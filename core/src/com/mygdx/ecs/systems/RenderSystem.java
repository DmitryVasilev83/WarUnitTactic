package com.mygdx.ecs.systems;

//import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.badlogic.gdx.scenes.scene2d.ui.List;
//import com.mygdx.ecs.entities.Entity;
//
//public class RenderSystem extends GameSystem {
//    private SpriteBatch batch;
//
//    @Override
//    public void render(SpriteBatch batch) {
//        List<Entity> renderableEntities = entityManager.getEntitiesWith(
//                RenderComponent.class, PositionComponent.class
//        );
//
//        renderableEntities.sort((a, b) ->
//                Integer.compare(
//                        a.getComponent(RenderComponent.class).zOrder,
//                        b.getComponent(RenderComponent.class).zOrder
//                )
//        );
//
//        for (Entity entity : renderableEntities) {
//            renderEntity(entity, batch);
//        }
//    }
//}
