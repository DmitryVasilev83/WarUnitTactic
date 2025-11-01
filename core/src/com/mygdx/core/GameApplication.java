package com.mygdx.core;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.ecs.entities.UnitDataManager;
import com.mygdx.game.battle.map.TileType;
import com.mygdx.game.states.GameStateManager;
import com.mygdx.game.states.LoadingState;

public class GameApplication extends Game {
	private SpriteBatch batch;
	private GameStateManager stateManager;
	private AssetManager assetManager;
	private UnitDataManager unitDataManager;

	@Override
	public void create() {
		batch = new SpriteBatch();
		assetManager = new AssetManager();
		unitDataManager = new UnitDataManager();

		// Загружаем юниты
		unitDataManager.loadUnits(assetManager);
		// Остальные ресурсы
		loadAssets();

		assetManager.finishLoading(); // Ждём загрузки всех ресурсов   !!!
		// Инициализация менеджера состояний
		stateManager = new GameStateManager(this);
		stateManager.pushState(new LoadingState(stateManager)); // Сначала загрузка
	}

	private void loadAssets() {
		// Загружаем текстуры тайлов
		for (TileType type : TileType.values()) {
			assetManager.load(type.getTexturePath(), Texture.class);
		}
	}

	@Override
	public void render() {
		float deltaTime = Gdx.graphics.getDeltaTime();

		stateManager.update(deltaTime);
		stateManager.render(batch);
	}

	@Override
	public void dispose() {
		batch.dispose();
		assetManager.dispose();
		stateManager.dispose();
	}

	@Override
	public void resize(int width, int height) {
		stateManager.resize(width, height); // новое
	}

	public SpriteBatch getBatch() {
		return batch;
	}

	public AssetManager getAssetManager() {
		return assetManager;
	}

	public UnitDataManager getUnitDataManager() { return unitDataManager; }
}

