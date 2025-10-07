package com.mygdx.ecs.entities;

import com.badlogic.gdx.assets.AssetManager;
import com.mygdx.core.GameApplication;
import com.mygdx.ecs.components.*;
import com.mygdx.game.data.units.UnitData;
import com.mygdx.game.data.units.Team;
import com.badlogic.gdx.graphics.Texture;

public class UnitFactory {

    public static Entity createUnit(String unitId, Team team, float worldX, float worldY, float tileSize, GameApplication app) {

        // log !!!
        System.out.println("UnitFactory.createUnit called with: " + unitId + ", " + team + ", world coords (" + worldX
                + "," + worldY + ")");

        UnitData data = app.getUnitDataManager().getUnitData(unitId);
        if (data == null) {
            throw new IllegalArgumentException("Unknown unit ID: " + unitId);
        }

        AssetManager assetManager = app.getAssetManager();

        Entity unit = new Entity();

        // Позиция
        unit.addComponent(new PositionComponent(worldX, worldY));
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
