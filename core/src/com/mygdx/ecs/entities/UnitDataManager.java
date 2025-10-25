package com.mygdx.ecs.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Json;
import com.mygdx.game.data.units.UnitData;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class UnitDataManager {
    private Map<String, UnitData> units = new HashMap<>();

    // Маппинг unitId -> путь к дефолтной текстуре для ручного создания
    private Map<String, String> defaultTexturePaths = new HashMap<>();

    public void loadUnits(AssetManager assetManager) {
        FileHandle unitsDir = Gdx.files.internal("units/");
        Json json = new Json();

        for (FileHandle file : unitsDir.list()) {
            if (file.extension().equals("json")) {
                UnitData data = json.fromJson(UnitData.class, file);
                units.put(data.id, data);

                // Определяем дефолтный путь к текстуре для этого типа юнита
                // (для будущего использования при ручном добавлении)
                String defaultTexturePath = "unitspng/" + data.id + ".png";
                defaultTexturePaths.put(data.id, defaultTexturePath);

                // Проверяем существование файла и загружаем
                FileHandle textureFile = Gdx.files.internal(defaultTexturePath);
                if (textureFile.exists()) {
                    assetManager.load(defaultTexturePath, Texture.class);
                    System.out.println("Queued texture for manual placement: " + defaultTexturePath);
                } else {
                    System.out.println("Warning: Default texture not found for " + data.id + ": " + defaultTexturePath);
                }

                System.out.println("Loaded unit data: " + data.id + " (HP: " + data.baseHealth + ")");
            }
        }

        System.out.println("Total unit types loaded: " + units.size());
    }

    public UnitData getUnitData(String id) {
        return units.get(id);
    }

    public Collection<UnitData> getAllUnits() {
        return units.values();
    }

    // Метод для получения пути к дефолтной текстуре для ручного создания юнита
    public String getDefaultTexturePath(String unitId) {
        return defaultTexturePaths.get(unitId);
    }
}

