package com.mygdx.ecs.entities;

import com.mygdx.ecs.components.*;
import com.mygdx.game.data.UnitType;
import com.mygdx.game.data.Team;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Color;

public class UnitFactory {

    public static Entity createUnit(UnitType type, Team team, int x, int y, float tileSize) {
        Entity unit = new Entity();

        // Позиция
        unit.addComponent(new PositionComponent(x, y, tileSize));

        // Рендер
        unit.addComponent(new RenderComponent(getTextureForType(type), tileSize));

        // Статы
        unit.addComponent(new UnitStatsComponent(type, team));

        // Выделяемость
        unit.addComponent(new SelectableComponent());

        // Движение
        unit.addComponent(new MovementComponent());

        // Бой
        unit.addComponent(new CombatComponent(type));

        // ИИ (только для AI)
        if (team == Team.AI) {
            unit.addComponent(new AIComponent());
        }

        return unit;
    }

    private static Texture getTextureForType(UnitType type) {
        switch (type) {
            case WARRIOR: return new Texture("units/warrior.png");
            case ARCHER:  return new Texture("units/archer.png");
            case MAGE:    return new Texture("units/mage.png");
            default: throw new IllegalArgumentException("Unknown unit type: " + type);
        }
    }
}
