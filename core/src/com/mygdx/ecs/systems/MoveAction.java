package com.mygdx.ecs.systems;

//public class MoveAction extends GameAction {
//    private Vector2 targetPosition;
//    private List<Vector2> path;
//
//    @Override
//    public boolean canExecute() {
//        UnitStatsComponent stats = performer.getComponent(UnitStatsComponent.class);
//        return stats.actionPoints >= actionPointCost &&
//                isValidPath(path) &&
//                isTargetPositionFree(targetPosition);
//    }
//
//    @Override
//    public void execute() {
//        // Анимация перемещения
//        // Обновление позиции
//        // Трата очков действия
//        EventManager.getInstance().fireEvent(new UnitMovedEvent(performer, targetPosition));
//    }
//}
