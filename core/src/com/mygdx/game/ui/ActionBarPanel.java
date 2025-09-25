package com.mygdx.game.ui;

//public class ActionBarPanel extends Table {
//    private List<ActionButton> actionButtons;
//
//    public void updateForUnit(Entity unit) {
//        clear();
//        actionButtons.clear();
//
//        CombatComponent combat = unit.getComponent(CombatComponent.class);
//        MovementComponent movement = unit.getComponent(MovementComponent.class);
//
//        if (movement.canMove) {
//            actionButtons.add(new ActionButton(ActionType.MOVE));
//        }
//
//        for (AbilityType ability : combat.abilities) {
//            actionButtons.add(new ActionButton(ActionType.ABILITY, ability));
//        }
//
//        layoutButtons();
//    }
//}
