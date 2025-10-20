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
    public String texturePath;
    public TiledMapTile tiledMapTile; // Тайл из Tiled
    public int tileGid = -1; // Новое поле для хранения GID
    public int startX;
    public int startY;
    public Team team;

    public boolean hasTiledMapTile() {
        return tiledMapTile != null || tileGid > 0;
    }

    public String getTexturePath() {
        return texturePath;
    }
}





