package com.mygdx.core;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.battle.TileType;
import com.mygdx.game.data.UnitType;
import com.mygdx.game.states.GameStateManager;
import com.mygdx.game.states.LoadingState;
import com.mygdx.game.states.MainMenuState;

import java.util.HashMap;
import java.util.Map;

public class GameApplication extends Game {
	private SpriteBatch batch;
	private GameStateManager stateManager;
	private AssetManager assetManager;

	@Override
	public void create() {
		batch = new SpriteBatch();
		assetManager = new AssetManager();
		loadAssets();

		// Инициализация менеджера состояний
		stateManager = new GameStateManager(this);
		stateManager.pushState(new LoadingState(stateManager)); // Сначала загрузка
	}

	private void loadAssets() {
		// Загружаем текстуры юнитов
		for (UnitType type : UnitType.values()) {
			assetManager.load(type.getTexturePath(), Texture.class);
		}

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

	public SpriteBatch getBatch() {
		return batch;
	}

	public AssetManager getAssetManager() {
		return assetManager;
	}
}

