package com.mygdx.ecs.systems;

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
