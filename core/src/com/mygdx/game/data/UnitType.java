package com.mygdx.game.data;

public enum UnitType {
    WARRIOR(100, 3, 5, 2, 1, AssetConfig.UNIT_WARRIOR),
    ARCHER(70, 4, 3, 3, 2, AssetConfig.UNIT_ARCHER),
    MAGE(50, 5, 2, 4, 3, AssetConfig.UNIT_MAGE);

    public final int baseHealth, initiative, movement, actionPoints, attackRange;
    private final String texturePath;

    UnitType(int health, int initiative, int movement, int ap, int range, String texturePath) {
        this.baseHealth = health;
        this.initiative = initiative;
        this.movement = movement;
        this.actionPoints = ap;
        this.attackRange = range;
        this.texturePath = texturePath;
    }

    public String getTexturePath() {
        return texturePath;
    }
}
