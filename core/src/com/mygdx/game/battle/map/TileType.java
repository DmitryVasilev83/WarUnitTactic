package com.mygdx.game.battle.map;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.data.AssetConfig;

public enum TileType {
    GRASS(AssetConfig.TILE_GRASS, 1f, true),
    FOREST(AssetConfig.TILE_FOREST, 1f, true),
    MOUNTAIN(AssetConfig.TILE_MOUNTAIN, 1f, true),
    WATER(AssetConfig.TILE_WATER, 1f, true);

    private final String texturePath;
    public final float movementCost;
    public final boolean isWalkable;
    private Texture texture; // будет загружено позже

    TileType(String texturePath, float movementCost, boolean isWalkable) {
        this.texturePath = texturePath;
        this.movementCost = movementCost;
        this.isWalkable = isWalkable;
    }

    public String getTexturePath() {
        return texturePath;
    }

    public void loadTexture(AssetManager assetManager) {
        if (assetManager.isLoaded(texturePath)) {
            this.texture = assetManager.get(texturePath, Texture.class);
        }
    }

    public Texture getTexture() {
        return texture;
    }
}


