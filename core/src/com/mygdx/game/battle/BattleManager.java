package com.mygdx.game.battle;

import com.sun.java.swing.action.ActionManager;

//public class BattleManager {
//    private TurnManager turnManager;
//    private GridMap map; // ← теперь используется
//    private EntityManager entityManager;
//    private ActionManager actionManager;
//    private EventManager eventManager;
//
//    public void startBattle(BattleConfiguration config) {
//        // Теперь получаем карту из BattleState или создаём по конфигу
//        // Например:
//        this.map = new GridMap((int)config.mapSize.x, (int)config.mapSize.y, 64f);
//
//        spawnUnits(config.playerUnits, config.enemyUnits);
//        turnManager.setMap(map); // передаём карту в TurnManager или MovementSystem
//        turnManager.calculateInitiative();
//        turnManager.startFirstTurn();
//    }
//
//    public GridMap getMap() {
//        return map;
//    }

//    public void update(float deltaTime) {
//        turnManager.update(deltaTime);
//        actionManager.processQueuedActions();
//        checkVictoryConditions();
//    }
//}

