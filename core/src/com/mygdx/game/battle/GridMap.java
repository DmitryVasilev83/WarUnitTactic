package com.mygdx.game.battle;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GridMap {
    private final int width;
    private final int height;
    private final Tile[][] tiles;
    private final float tileSize;

    public GridMap(int width, int height, float tileSize) {
        this.width = width;
        this.height = height;
        this.tileSize = tileSize;
        this.tiles = new Tile[width][height];

        // Заполняем карту
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                TileType type;

                // Пример генерации: шахматная доска + препятствия
                if (x == 0 || y == 0 || x == width - 1 || y == height - 1) {
                    type = TileType.MOUNTAIN;
                } else if ((x + y) % 4 == 0) {
                    type = TileType.FOREST;
                } else if ((x + y) % 7 == 0) {
                    type = TileType.WATER;
                } else {
                    type = TileType.GRASS;
                }

                tiles[x][y] = new Tile(x, y, type);
            }
        }
    }

    public Tile getTile(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height) return null;
        return tiles[x][y];
    }

    public void render(SpriteBatch batch) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Tile tile = tiles[x][y];
                if (tile != null && tile.type.texture != null) {
                    float worldX = x * tileSize;
                    float worldY = y * tileSize;
                    batch.draw(tile.type.texture, worldX, worldY, tileSize, tileSize);
                }
            }
        }
    }

    public float getTileSize() { return tileSize; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
}