package com.mygdx.game.battle;

//import com.badlogic.gdx.scenes.scene2d.ui.List;
//import com.badlogic.gdx.utils.Queue;
//import com.mygdx.ecs.entities.Entity;

//public class TurnManager {
//    private Queue<Entity> turnOrder;
//    private Entity currentUnit;
//    private TurnPhase currentPhase;
//
//    public void calculateInitiative() {
//        List<Entity> allUnits = entityManager.getEntitiesWith(UnitStatsComponent.class);
//        allUnits.sort((a, b) -> Integer.compare(
//                b.getComponent(UnitStatsComponent.class).initiative,
//                a.getComponent(UnitStatsComponent.class).initiative
//        ));
//
//        turnOrder = new LinkedList<>(allUnits);
//    }
//
//    public void nextTurn() {
//        if (currentUnit != null) {
//            resetUnitForNewTurn(currentUnit);
//        }
//
//        currentUnit = turnOrder.poll();
//        turnOrder.offer(currentUnit); // Возвращаем в конец очереди
//
//        EventManager.getInstance().fireEvent(new TurnStartEvent(currentUnit));
//    }
//}
