package com.mygdx.game.battle;

//import com.badlogic.gdx.assets.AssetManager;
//import com.badlogic.gdx.graphics.g2d.TextureRegion;
//
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
