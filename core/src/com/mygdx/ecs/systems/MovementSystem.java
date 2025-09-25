package com.mygdx.ecs.systems;

//import com.badlogic.gdx.math.Vector2;
//import com.badlogic.gdx.scenes.scene2d.ui.List;
//import com.mygdx.ecs.components.PositionComponent;
//import com.mygdx.ecs.entities.Entity;
//import com.mygdx.game.battle.GridMap;

//import java.util.ArrayList;
//
//public class MovementSystem extends GameSystem {
//    private GridMap gridMap;
//
//    public void setGridMap(GridMap map) {
//        this.gridMap = map;
//    }
//
//    @Override
//    public void update(float deltaTime) {
//        if (gridMap == null) return;
//
//        List<Entity> movableUnits = entityManager.getEntitiesWith(
//                MovementComponent.class, PositionComponent.class, UnitStatsComponent.class
//        );
//
//        for (Entity unit : movableUnits) {
//            PositionComponent pos = unit.getComponent(PositionComponent.class);
//            MovementComponent move = unit.getComponent(MovementComponent.class);
//
//            // Обновляем доступные ходы с учётом карты
//            move.availableMoves = calculateAvailableMoves(
//                    pos.gridX, pos.gridY,
//                    unit.getComponent(UnitStatsComponent.class).movementRange,
//                    gridMap
//            );
//        }
//    }
//
//    private List<Vector2> calculateAvailableMoves(int startX, int startY, int range, GridMap map) {
//        // Реализуй A* или BFS с учётом movementCost и isWalkable
//        // Это отдельная задача, но базовая логика:
//        List<Vector2> result = new ArrayList<>();
//        // ... алгоритм поиска ...
//        return result;
//    }
//}