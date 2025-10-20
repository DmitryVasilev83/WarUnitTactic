package com.mygdx.game.battle.map;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class GridMap {
    private final int width;
    private final int height;
    private final Tile[][] tiles;
    private final float tileSize;
    private AssetManager assetManager;
    private TiledMap tiledMap; // Новое поле

    public GridMap(int width, int height, Tile[][] tiles, AssetManager assetManager, float tileSize) {
        this.width = width;
        this.height = height;
        this.tiles = tiles;
        this.tileSize = tileSize;
        this.assetManager = assetManager;

        // Загружаем текстуры для типов тайлов (fallback)
        for (TileType type : TileType.values()) {
            type.loadTexture(assetManager);
        }
    }

    // Новый метод для установки TiledMap
    public void setTiledMap(TiledMap tiledMap) {
        this.tiledMap = tiledMap;
    }

    // Новый метод для получения TiledMap
    public TiledMap getTiledMap() {
        return tiledMap;
    }

    public Tile getTile(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height) return null;
        return tiles[x][y];
    }

    public void render(SpriteBatch batch) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Tile tile = tiles[x][y];
                if (tile != null) {
                    float worldX = x * tileSize;
                    float worldY = y * tileSize;

                    // Приоритет: TMX текстура > fallback текстура
                    if (tile.textureRegion != null && tile.textureRegion.getTexture() != null) {
                        // Используем текстуру из TMX
                        batch.draw(tile.textureRegion, worldX, worldY, tileSize, tileSize);
                    } else if (tile.type != null && tile.type.getTexture() != null) {
                        // Fallback на тип тайла
                        batch.draw(tile.type.getTexture(), worldX, worldY, tileSize, tileSize);
                    }
                }
            }
        }
    }

    public float getTileSize() { return tileSize; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
}