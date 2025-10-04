package com.mygdx.game.data;

public class UnitData {
    public String id;
    public String name;
    public int baseHealth;
    public int initiative;
    public int movement;
    public int actionPoints;
    public int attackRange;
    public String texturePath;

    public UnitData() {}

    public String getId() { return id; }
    public String getName() { return name; }
    public String getTexturePath() { return texturePath; }
}
