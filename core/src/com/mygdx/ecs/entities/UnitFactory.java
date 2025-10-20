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

    public static Entity createUnit(String unitId, Team team, float worldX, float worldY, float tileSize, GameApplication app, TiledMap tiledMap) {
        // Получаем базовые данные из UnitDataManager
        UnitData baseData = app.getUnitDataManager().getUnitData(unitId);
        if (baseData == null) {
            throw new IllegalArgumentException("Unknown unit ID: " + unitId);
        }

        AssetManager assetManager = app.getAssetManager();
        Entity unit = new Entity();

        // Позиция
        unit.addComponent(new PositionComponent(worldX, worldY));

        // Рендер - с поддержкой тайлов из Tiled
        RenderComponent renderComponent = createRenderComponent(baseData, assetManager, tileSize, tiledMap);
        unit.addComponent(renderComponent);

        // Остальные компоненты (передаем baseData, так как у нас есть все нужные данные)
        unit.addComponent(new UnitStatsComponent(baseData, team));
        unit.addComponent(new SelectableComponent());
        unit.addComponent(new MovementComponent());
        unit.addComponent(new CombatComponent(baseData));

        if (team == Team.AI) {
            unit.addComponent(new AIComponent());
        }

        return unit;
    }

    private static RenderComponent createRenderComponent(UnitData data, AssetManager assetManager, float tileSize, TiledMap tiledMap) {
        System.out.println("Creating render component for unit: " + data.id + ", hasGID: " + (data.tileGid > 0));

        // Если есть GID из Tiled, восстанавливаем тайл
        if (data.tileGid > 0 && tiledMap != null) {
            TiledMapTile tiledMapTile = getTileByGid(tiledMap, data.tileGid);
            if (tiledMapTile != null) {
                System.out.println("Using tile from Tiled map for unit: " + data.id + " with GID: " + data.tileGid);
                return new RenderComponent(tiledMapTile, tileSize);
            } else {
                System.out.println("Failed to find tile for GID: " + data.tileGid);
            }
        }

        // Фолбэк на обычную текстуру из JSON
        System.out.println("Using texture from JSON for unit: " + data.id + ": " + data.getTexturePath());
        if (data.getTexturePath() != null && assetManager.isLoaded(data.getTexturePath())) {
            Texture texture = assetManager.get(data.getTexturePath(), Texture.class);
            return new RenderComponent(texture, tileSize);
        }

        // Если ничего не удалось загрузить, создаем дефолтную текстуру
        System.out.println("Using default texture for unit: " + data.id);
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

