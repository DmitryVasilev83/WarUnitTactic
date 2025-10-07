package com.mygdx.game.battle.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Tile {
    public int x, y;
    public TileType type;
    public TextureRegion textureRegion;

    public Tile(int x, int y, TileType type) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.textureRegion = null;
    }

    public Tile(int x, int y, TileType type, TextureRegion textureRegion) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.textureRegion = textureRegion;
    }

    public void setTextureRegion(TextureRegion region) {
        this.textureRegion = region;
    }
}

