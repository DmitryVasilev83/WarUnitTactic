package com.mygdx.game.battle;

import com.mygdx.utils.Vector2i;

public class Tile {
    public final Vector2i position;
    public final TileType type;

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

