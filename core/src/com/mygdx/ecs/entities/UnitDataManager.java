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

        Json json = new Json();

        for (FileHandle file : unitsDir.list()) {
            if (file.extension().equals("json")) {
                UnitData data = json.fromJson(UnitData.class, file);
                units.put(data.id, data);

                // Загружаем текстуру
                assetManager.load(data.texturePath, Texture.class);
            }
        }
    }

    public UnitData getUnitData(String id) {
        return units.get(id);
    }

    public Collection<UnitData> getAllUnits() {
        return units.values();
    }
}

//public class UnitDataManager {
//    private Map<String, UnitData> units = new HashMap<>();
//
//    public void loadUnits(AssetManager assetManager) {
//        FileHandle unitsDir = Gdx.files.internal("units/");
//
//        Json json = new Json();
//
//        for (FileHandle file : unitsDir.list()) {
//            if (file.extension().equals("json")) {
//                UnitData data = json.fromJson(UnitData.class, file);
//                if (data.id != null && !data.id.trim().isEmpty()) {
//                    units.put(data.id, data);
//                    // Загружаем текстуру
//                    assetManager.load(data.texturePath, Texture.class);
//                } else {
//                    System.out.println("Пропущен юнит без ID: " + file.name());
//                }
//            }
//        }
//    }
//
//    public UnitData getUnitData(String id) {
//        return units.get(id);
//    }
//
//    public Collection<UnitData> getAllUnits() {
//        return units.values();
//    }
//}
