package com.mygdx.ecs.entities;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.mygdx.core.GameApplication;
import com.mygdx.ecs.components.*;
import com.mygdx.game.battle.map.GridMap;
import com.mygdx.game.data.units.UnitData;
import com.mygdx.game.data.units.Team;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.states.BattleState;
import com.mygdx.game.states.GameState;


public class UnitFactory {

    public static Entity createUnit(String unitId, Team team, float worldX, float worldY,
                                    float tileSize, GameApplication app, TiledMap tiledMap) {
        UnitData baseData = app.getUnitDataManager().getUnitData(unitId);
        if (baseData == null) {
            throw new IllegalArgumentException("Unknown unit ID: " + unitId);
        }

        AssetManager assetManager = app.getAssetManager();
        Entity unit = new Entity();

        unit.addComponent(new PositionComponent(worldX, worldY));

        // Рендер компонент создается на основе данных из baseData
        RenderComponent renderComponent = createRenderComponent(baseData, assetManager, tileSize, tiledMap);
        unit.addComponent(renderComponent);

        unit.addComponent(new UnitStatsComponent(baseData, team));
        unit.addComponent(new SelectableComponent());
        unit.addComponent(new MovementComponent());
        unit.addComponent(new CombatComponent(baseData));

        if (team == Team.AI) {
            unit.addComponent(new AIComponent());
        }

        return unit;
    }

    // Перегруженный метод для создания юнита с UnitData (из карты)
    public static Entity createUnitFromData(UnitData unitData, float tileSize,
                                            GameApplication app, TiledMap tiledMap) {
        float worldX = unitData.startX * tileSize;
        float worldY = unitData.startY * tileSize;

        AssetManager assetManager = app.getAssetManager();
        Entity unit = new Entity();

        unit.addComponent(new PositionComponent(worldX, worldY));

        // Используем unitData напрямую (там уже есть GID или manualTexturePath)
        RenderComponent renderComponent = createRenderComponent(unitData, assetManager, tileSize, tiledMap);
        unit.addComponent(renderComponent);

        unit.addComponent(new UnitStatsComponent(unitData, unitData.team));
        unit.addComponent(new SelectableComponent());
        unit.addComponent(new MovementComponent());
        unit.addComponent(new CombatComponent(unitData));

        if (unitData.team == Team.AI) {
            unit.addComponent(new AIComponent());
        }

        return unit;
    }

    // Метод для создания юнита вручную с указанием текстуры
    public static Entity createUnitManual(String unitId, Team team, float worldX, float worldY,
                                          String texturePath, float tileSize, GameApplication app) {
        UnitData baseData = app.getUnitDataManager().getUnitData(unitId);
        if (baseData == null) {
            throw new IllegalArgumentException("Unknown unit ID: " + unitId);
        }

        // Создаем копию и устанавливаем путь к текстуре
        UnitData unitData = baseData.copy();
        unitData.manualTexturePath = texturePath;
        unitData.team = team;
        unitData.tileGid = -1; // Явно указываем, что GID не используется

        AssetManager assetManager = app.getAssetManager();
        Entity unit = new Entity();

        unit.addComponent(new PositionComponent(worldX, worldY));

        RenderComponent renderComponent = createRenderComponent(unitData, assetManager, tileSize, null);
        unit.addComponent(renderComponent);

        unit.addComponent(new UnitStatsComponent(unitData, team));
        unit.addComponent(new SelectableComponent());
        unit.addComponent(new MovementComponent());
        unit.addComponent(new CombatComponent(unitData));

        if (team == Team.AI) {
            unit.addComponent(new AIComponent());
        }

        return unit;
    }

    private static RenderComponent createRenderComponent(UnitData data, AssetManager assetManager,
                                                         float tileSize, TiledMap tiledMap) {
        System.out.println("Creating render component for unit: " + data.id);

        // Приоритет 1: GID из Tiled
        if (data.tileGid > 0 && tiledMap != null) {
            TiledMapTile tiledMapTile = getTileByGid(tiledMap, data.tileGid);
            if (tiledMapTile != null) {
                System.out.println("✓ Using tile from Tiled map for unit: " + data.id + " with GID: " + data.tileGid);
                return new RenderComponent(tiledMapTile, tileSize);
            } else {
                System.out.println("✗ Failed to find tile for GID: " + data.tileGid);
            }
        }

        // Приоритет 2: Ручной путь к текстуре
        if (data.manualTexturePath != null && !data.manualTexturePath.isEmpty()) {
            if (assetManager.isLoaded(data.manualTexturePath)) {
                Texture texture = assetManager.get(data.manualTexturePath, Texture.class);
                System.out.println("✓ Using manual texture for unit: " + data.id + ": " + data.manualTexturePath);
                return new RenderComponent(texture, tileSize);
            } else {
                System.out.println("✗ Manual texture not loaded: " + data.manualTexturePath);
            }
        }

        // Приоритет 3: Дефолтная текстура
        System.out.println("⚠ Using default texture for unit: " + data.id);
        return new RenderComponent(createDefaultTexture(), tileSize);
    }

    private static TiledMapTile getTileByGid(TiledMap tiledMap, int gid) {
        for (TiledMapTileSet tileSet : tiledMap.getTileSets()) {
            TiledMapTile tile = tileSet.getTile(gid);
            if (tile != null) {
                System.out.println("Found tile in tileset: " + tileSet.getName());
                return tile;
            }
        }
        System.out.println("Tile with GID " + gid + " not found in any tileset");
        return null;
    }

    private static Texture createDefaultTexture() {
        Pixmap pixmap = new Pixmap(32, 32, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.MAGENTA);
        pixmap.fill();
        pixmap.setColor(Color.BLACK);
        pixmap.drawLine(0, 0, 32, 32);
        pixmap.drawLine(32, 0, 0, 32);
        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        return texture;
    }
}
