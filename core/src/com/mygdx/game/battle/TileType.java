package com.mygdx.game.battle;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public enum TileType {
    GRASS("tiles/grass.png", 1.0f, true),
    WATER("tiles/water.png", 3.0f, false),
    FOREST("tiles/forest.png", 1.5f, true),
    MOUNTAIN("tiles/mountain.png", 0.0f, false);

    public final String texturePath;
    public final float movementCost;
    public final boolean isWalkable;
    public TextureRegion texture;

    TileType(String texturePath, float movementCost, boolean isWalkable) {
        this.texturePath = texturePath;
        this.movementCost = movementCost;
        this.isWalkable = isWalkable;
    }

    // Вызывается после загрузки ассетов
    public static void loadTextures() {
        for (TileType type : values()) {
            Texture tex = new Texture(type.texturePath);
            tex.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
            type.texture = new TextureRegion(tex);
        }
    }

    public static void disposeTextures() {
        for (TileType type : values()) {
            if (type.texture != null && type.texture.getTexture() != null) {
                type.texture.getTexture().dispose();
            }
        }
    }
}

// Реализация через AssetManager
//public enum TileType {
//    GRASS(1.0f, true),
//    WATER(3.0f, false),
//    FOREST(1.5f, true),
//    MOUNTAIN(0.0f, false); // непроходимо
//
//    public final float movementCost;
//    public final boolean isWalkable;
//    public TextureRegion texture;
//
//    TileType(float movementCost, boolean isWalkable) {
//        this.movementCost = movementCost;
//        this.isWalkable = isWalkable;
//    }
//
//    // Инициализация текстур (вызывается после загрузки ассетов)
//    public static void loadTextures(AssetManager assetManager) {
//        GRASS.texture = assetManager.get("tiles/grass.png", TextureRegion.class);
//        WATER.texture = assetManager.get("tiles/water.png", TextureRegion.class);
//        FOREST.texture = assetManager.get("tiles/forest.png", TextureRegion.class);
//        MOUNTAIN.texture = assetManager.get("tiles/mountain.png", TextureRegion.class);
//    }
//}
