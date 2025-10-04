package com.mygdx.ecs.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Json;
import com.mygdx.game.data.UnitData;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class UnitDataManager {
    private Map<String, UnitData> units = new HashMap<>();

    public void loadUnits(AssetManager assetManager) {
        FileHandle unitsDir = Gdx.files.internal("units/");
        System.out.println("Файлы в units/: " + Arrays.toString(unitsDir.list()));
        Json json = new Json();

        for (FileHandle file : unitsDir.list()) {

            if (file.extension().equals("json")) {
                System.out.println("Чтение файла: " + file.name());

                UnitData data = json.fromJson(UnitData.class, file);

                System.out.println("ID: " + data.id + ", Name: " + data.name);
//                units.put(data.id, data);
                if (data.id != null && !data.id.trim().isEmpty()) {
                    units.put(data.id, data);
                    System.out.println("Загружен юнит: " + data.id);
                } else {
                    System.out.println("Пропущен юнит без ID: " + file.name());
                }

                // Загружаем текстуру
                assetManager.load(data.texturePath, Texture.class);
            }
        }
    }

    public UnitData getUnitData(String id) {
        System.out.println("Поиск юнита: " + id + ", найдено: " + units.get(id));
        return units.get(id);
    }

    public Collection<UnitData> getAllUnits() {
        return units.values();
    }
}
