package com.mygdx.game.data;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.battle.map.GridMap;
import com.mygdx.game.battle.map.Tile;
import com.mygdx.game.battle.map.TileType;
import com.mygdx.game.data.units.Team;
import com.mygdx.game.data.units.UnitData;

import java.util.Iterator;

public class MapLoader {

    public static class MapData {
        public GridMap gridMap;
        public UnitData[] units;
        public float tileWidth;   // Добавляем размеры тайла
        public float tileHeight;

        public MapData(GridMap gridMap, UnitData[] units, float tileWidth, float tileHeight) {
            this.gridMap = gridMap;
            this.units = units;
            this.tileWidth = tileWidth;
            this.tileHeight = tileHeight;
        }
    }

    public static MapData loadMap(String mapPath, AssetManager assetManager) {
        TmxMapLoader mapLoader = new TmxMapLoader();
        TiledMap tiledMap = mapLoader.load(mapPath);

        // Получаем реальный размер тайла из карты
        int mapTileWidth = (int) tiledMap.getProperties().get("tilewidth", Integer.class);
        int mapTileHeight = (int) tiledMap.getProperties().get("tileheight", Integer.class);

        System.out.println("Map tile size: " + mapTileWidth + "x" + mapTileHeight);

        // Загрузка тайлов
        GridMap gridMap = loadGridMap(tiledMap, assetManager);

        // Загрузка юнитов
        UnitData[] units = loadUnits(tiledMap, mapTileWidth, mapTileHeight);

        return new MapData(gridMap, units, mapTileWidth, mapTileHeight);
    }

    private static GridMap loadGridMap(TiledMap tiledMap, AssetManager assetManager) {
        int width = (int) tiledMap.getProperties().get("width", Integer.class);
        int height = (int) tiledMap.getProperties().get("height", Integer.class);
        int tileWidth = (int) tiledMap.getProperties().get("tilewidth", Integer.class);
        int tileHeight = (int) tiledMap.getProperties().get("tileheight", Integer.class);

        System.out.println("=== LOADING GRID MAP ===");
        System.out.println("Map size: " + width + "x" + height + ", Tile size: " + tileWidth + "x" + tileHeight);

        Tile[][] tiles = new Tile[width][height];

        TiledMapTileLayer tileLayer = (TiledMapTileLayer) tiledMap.getLayers().get("relief");
        if (tileLayer == null) {
            throw new RuntimeException("Layer 'relief' not found in map.");
        }

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                TiledMapTileLayer.Cell cell = tileLayer.getCell(x, y);
                TileType tileType = TileType.GRASS;

                if (cell != null) {
                    String tileTypeStr = (String) cell.getTile().getProperties().get("tileType");
                    if (tileTypeStr != null) {
                        try {
                            tileType = TileType.valueOf(tileTypeStr.toUpperCase());
                        } catch (IllegalArgumentException e) {
                            System.err.println("Unknown tileType: " + tileTypeStr);
                        }
                    }

                    // Создаем тайл с типом
                    tiles[x][y] = new Tile(x, y, tileType);

                    // Устанавливаем текстуру из TMX
                    TiledMapTile tiledMapTile = cell.getTile();
                    if (tiledMapTile != null) {
                        TextureRegion textureRegion = tiledMapTile.getTextureRegion();
                        if (textureRegion != null) {
                            tiles[x][y].setTextureRegion(new TextureRegion(textureRegion));
                        }
                    }
                } else {
                    tiles[x][y] = new Tile(x, y, tileType);
                }
            }
        }

        // Передаем реальный размер тайла в GridMap
        return new GridMap(width, height, tiles, assetManager, tileWidth);
    }

    private static UnitData[] loadUnits(TiledMap tiledMap, int tileWidth, int tileHeight) {
        System.out.println("=== LOADING UNITS ===");
        System.out.println("Using tile size: " + tileWidth + "x" + tileHeight);

        MapLayer objectLayer = tiledMap.getLayers().get("units");
        if (objectLayer == null) {
            System.out.println("Layer 'units' not found!");
            return new UnitData[0];
        }

        java.util.ArrayList<UnitData> units = new java.util.ArrayList<>();

        for (int i = 0; i < objectLayer.getObjects().getCount(); i++) {
            MapObject obj = objectLayer.getObjects().get(i);
            MapProperties props = obj.getProperties();

            String unitTypeStr = (String) props.get("unitType");
            String teamStr = (String) props.get("team");

            if (unitTypeStr != null && teamStr != null) {
                // Получаем координаты правильно
                float x = 0f, y = 0f;

                Object xObj = props.get("x");
                Object yObj = props.get("y");

                if (xObj instanceof Number) {
                    x = ((Number) xObj).floatValue();
                }
                if (yObj instanceof Number) {
                    y = ((Number) yObj).floatValue();
                }

                // Конвертируем пиксельные координаты в тайловые
                int tileX = (int) (x / tileWidth);
                int tileY = (int) (y / tileHeight);

                System.out.println("Unit: " + unitTypeStr + " at pixel (" + x + ", " + y + ") -> tile (" + tileX + ", " + tileY + ")");

                UnitData unitData = new UnitData();
                unitData.id = unitTypeStr;
                unitData.startX = tileX;
                unitData.startY = tileY;

                try {
                    unitData.team = Team.valueOf(teamStr.toUpperCase());
                    units.add(unitData);
                } catch (IllegalArgumentException e) {
                    System.err.println("Invalid team: " + teamStr);
                }
            }
        }

        System.out.println("Total units loaded: " + units.size());
        return units.toArray(new UnitData[0]);
    }
}