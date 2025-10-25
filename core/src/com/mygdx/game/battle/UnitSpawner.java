package com.mygdx.game.battle;

import com.badlogic.gdx.Gdx;
import com.mygdx.core.GameApplication;
import com.mygdx.ecs.entities.Entity;
import com.mygdx.ecs.entities.EntityManager;
import com.mygdx.ecs.entities.UnitDataManager;
import com.mygdx.ecs.entities.UnitFactory;
import com.mygdx.game.data.units.Team;

public class UnitSpawner {
    private final EntityManager entityManager;
    private final float tileSize;

    public UnitSpawner(EntityManager entityManager, float tileSize) {
        this.entityManager = entityManager;
        this.tileSize = tileSize;
    }

    public Entity spawnUnit(String unitId, Team team, int tileX, int tileY, String texturePath) {
        float worldX = tileX * tileSize;
        float worldY = tileY * tileSize;

        Entity unit = UnitFactory.createUnitManual(
                unitId,
                team,
                worldX,
                worldY,
                texturePath,
                tileSize,
                (GameApplication) Gdx.app.getApplicationListener()
        );

        entityManager.addEntity(unit);
        return unit;
    }

    // Дополнительные методы для разных способов создания юнитов
//    public Entity spawnUnitAtWorldPosition(String unitId, Team team, float worldX, float worldY) {
//        // Реализация
//    }

//    public void spawnUnitsFromFormation(List<UnitSpawnData> formation) {
//        // Массовое создание юнитов
//    }
}
