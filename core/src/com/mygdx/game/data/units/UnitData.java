package com.mygdx.game.data.units;

public class UnitData {
    public String id;
    public String name;
    public int baseHealth;
    public int initiative;
    public int movement;
    public int actionPoints;
    public int attackRange;
    public String texturePath;
    public int startX, startY;
    public Team team;

    public UnitData() {}

    public String getId() { return id; }
    public String getName() { return name; }
    public String getTexturePath() { return texturePath; }
}



