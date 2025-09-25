package com.mygdx.ecs.systems;

//public class ActionManager {
//    private Queue<GameAction> actionQueue;
//    private GameAction currentAction;
//
//    public void queueAction(GameAction action) {
//        if (action.canExecute()) {
//            actionQueue.offer(action);
//        }
//    }
//
//    public void processQueuedActions() {
//        if (currentAction == null && !actionQueue.isEmpty()) {
//            currentAction = actionQueue.poll();
//            currentAction.execute();
//            currentAction = null; // Для простых действий
//        }
//    }
//}