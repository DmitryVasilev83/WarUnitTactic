package com.mygdx.game.data.units;


import com.badlogic.gdx.maps.tiled.TiledMapTile;

public class UnitData {
    public String id;
    public String name;
    public int baseHealth;
    public int initiative;
    public int movement;
    public int actionPoints;
    public int attackRange;

    // Данные для размещения на карте
    public int startX;
    public int startY;
    public Team team;

    // Данные о текстуре (взаимоисключающие)
    public int tileGid = -1;           // GID из Tiled (приоритет)
    public String manualTexturePath;    // Путь для ручного добавления

    // Метод для клонирования базовых данных
    public UnitData copy() {
        UnitData copy = new UnitData();
        copy.id = this.id;
        copy.name = this.name;
        copy.baseHealth = this.baseHealth;
        copy.initiative = this.initiative;
        copy.movement = this.movement;
        copy.actionPoints = this.actionPoints;
        copy.attackRange = this.attackRange;
        return copy;
    }
}




