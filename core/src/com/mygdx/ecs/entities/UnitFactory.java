package com.mygdx.ecs.entities;

import com.badlogic.gdx.assets.AssetManager;
import com.mygdx.core.GameApplication;
import com.mygdx.ecs.components.*;
import com.mygdx.game.data.UnitData;
import com.mygdx.game.data.UnitType;
import com.mygdx.game.data.Team;
import com.badlogic.gdx.graphics.Texture;


public class UnitFactory {
    public static Entity createUnit(String unitId, Team team, int x, int y, float tileSize, GameApplication app) {
        UnitData data = app.getUnitDataManager().getUnitData(unitId);
        if (data == null) {
            throw new IllegalArgumentException("Unknown unit ID: " + unitId);
        }

        AssetManager assetManager = app.getAssetManager();

        Entity unit = new Entity();

        // Позиция
        unit.addComponent(new PositionComponent(x, y, tileSize));
        // Рендер
        unit.addComponent(new RenderComponent(assetManager.get(data.getTexturePath(), Texture.class), tileSize));
        // Статы
        unit.addComponent(new UnitStatsComponent(data, team));
        // Выделяемость
        unit.addComponent(new SelectableComponent());
        // Движение
        unit.addComponent(new MovementComponent());
        // Бой
        unit.addComponent(new CombatComponent(data));
        // ИИ (если AI)
        if (team == Team.AI) {
            unit.addComponent(new AIComponent());
        }

        return unit;
    }
}
// Удалить после проверки юнитов через JSON
//public class UnitFactory {
//
//    public static Entity createUnit(UnitType type, Team team, int x, int y, float tileSize, AssetManager assetManager) {
//        Entity unit = new Entity();
//        // Позиция
//        unit.addComponent(new PositionComponent(x, y, tileSize));
//        // Рендер
//        unit.addComponent(new RenderComponent(assetManager.get(type.getTexturePath(), Texture.class), tileSize));
//
//        // Статы
//        unit.addComponent(new UnitStatsComponent(type, team));
//        // Выделяемость
//        unit.addComponent(new SelectableComponent());
//        // Движение
//        unit.addComponent(new MovementComponent());
//        // Бой
//        unit.addComponent(new CombatComponent(type));
//
//        // ИИ (только для AI)
//        if (team == Team.AI) {
//            unit.addComponent(new AIComponent());
//        }
//
//        return unit;
//    }
//
//}
