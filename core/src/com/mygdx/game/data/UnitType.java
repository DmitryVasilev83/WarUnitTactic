package com.mygdx.game.data;

public enum UnitType {
    WARRIOR(100, 3, 5, 2, 1),
    ARCHER(70, 4, 3, 3, 2),
    MAGE(50, 5, 2, 4, 3);

    public final int baseHealth, initiative, movement, actionPoints, attackRange;

    UnitType(int health, int initiative, int movement, int ap, int range) {
        this.baseHealth = health;
        this.initiative = initiative;
        this.movement = movement;
        this.actionPoints = ap;
        this.attackRange = range;
    }
}
