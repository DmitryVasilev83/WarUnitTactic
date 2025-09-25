package com.mygdx.game.data;

//public class GameInputProcessor implements InputProcessor {
//    private BattleState battleState;
//    private UIManager uiManager;
//
//    @Override
//    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
//        Vector2 worldCoords = screenToWorld(screenX, screenY);
//        Vector2 gridCoords = worldToGrid(worldCoords);
//
//        if (button == Input.Buttons.LEFT) {
//            handleLeftClick(gridCoords);
//        } else if (button == Input.Buttons.RIGHT) {
//            handleRightClick(gridCoords);
//        }
//
//        return true;
//    }
//
//    private void handleLeftClick(Vector2 gridCoords) {
//        Entity clickedUnit = getUnitAtPosition(gridCoords);
//
//        if (clickedUnit != null) {
//            selectUnit(clickedUnit);
//        } else {
//            executeSelectedAction(gridCoords);
//        }
//    }
//
//    private void handleRightClick(Vector2 gridCoords) {
//        Entity clickedUnit = getUnitAtPosition(gridCoords);
//
//        if (clickedUnit != null) {
//            uiManager.showUnitInfo(clickedUnit);
//        }
//    }
//}
