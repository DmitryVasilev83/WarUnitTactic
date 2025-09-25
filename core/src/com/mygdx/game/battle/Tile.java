package com.mygdx.game.battle;


import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.utils.Vector2i;
import com.badlogic.gdx.graphics.Color;

public class Tile {
    public final Vector2i position;
    public TileType type;

    public Tile(int x, int y, TileType type) {
        this.position = new Vector2i(x, y);
        this.type = type;
    }

    public boolean isWalkable() {
        return type.isWalkable;
    }

    public float getMovementCost() {
        return type.movementCost;
    }
}


