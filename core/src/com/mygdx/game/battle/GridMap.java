package com.mygdx.game.battle;

//import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.mygdx.utils.Vector2i;
//
//public class GridMap {
//    private final int width;
//    private final int height;
//    private final Tile[][] tiles;
//    private final float tileSize; // размер квадрата на карте
//
//    public GridMap(int width, int height, float tileSize) {
//        this.width = width;
//        this.height = height;
//        this.tileSize = tileSize;
//        this.tiles = new Tile[width][height];
//
//        // Инициализация по умолчанию — трава
//        for (int x = 0; x < width; x++) {
//            for (int y = 0; y < height; y++) {
//                tiles[x][y] = new Tile(x, y, TileType.GRASS);
//            }
//        }
//    }
//
//    public Tile getTile(int x, int y) {
//        if (x < 0 || x >= width || y < 0 || y >= height) {
//            return null;
//        }
//        return tiles[x][y];
//    }
//
//    public Tile getTile(Vector2i pos) {
//        return getTile(pos.x, pos.y);
//    }
//
//    public boolean isWalkable(int x, int y) {
//        Tile tile = getTile(x, y);
//        return tile != null && tile.isWalkable;
//    }
//
//    public float getMovementCost(int x, int y) {
//        Tile tile = getTile(x, y);
//        return tile != null ? tile.movementCost : Float.MAX_VALUE;
//    }
//
//    // Конвертация мировых координат → сетка
//    public Vector2i worldToGrid(float worldX, float worldY) {
//        int gridX = (int) (worldX / tileSize);
//        int gridY = (int) (worldY / tileSize);
//        return new Vector2i(gridX, gridY);
//    }
//
//    // Конвертация сетки → мировые координаты (центр тайла)
//    public float gridToWorldX(int gridX) {
//        return gridX * tileSize + tileSize / 2f;
//    }
//
//    public float gridToWorldY(int gridY) {
//        return gridY * tileSize + tileSize / 2f;
//    }
//
//    public float getTileSize() {
//        return tileSize;
//    }
//
//    public int getWidth() { return width; }
//    public int getHeight() { return height; }
//
//    // Отрисовка карты (можно вынести в RenderSystem, но пока здесь)
//    public void render(SpriteBatch batch) {
//        for (int x = 0; x < width; x++) {
//            for (int y = 0; y < height; y++) {
//                Tile tile = tiles[x][y];
//                if (tile != null && tile.texture != null) {
//                    float worldX = x * tileSize;
//                    float worldY = y * tileSize;
//                    batch.draw(tile.texture, worldX, worldY, tileSize, tileSize);
//                }
//            }
//        }
//    }
//}