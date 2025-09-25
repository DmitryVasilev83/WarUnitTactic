package com.mygdx.core;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.battle.TileType;
import com.mygdx.game.states.GameStateManager;
import com.mygdx.game.states.MainMenuState;

public class GameApplication extends Game {
	private SpriteBatch batch;
	private GameStateManager stateManager;
//	private AssetManager assetManager;

	@Override
	public void create() {
		batch = new SpriteBatch();
//		assetManager = new AssetManager();

//		loadAssets();

		TileType.loadTextures();
		// Инициализация менеджера состояний
		stateManager = new GameStateManager(this);
		stateManager.pushState(new MainMenuState(stateManager));

//		setupInputProcessing();
	}

	@Override
	public void render() {
		float deltaTime = Gdx.graphics.getDeltaTime();

//		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// Обновление и рендеринг текущего состояния
		stateManager.update(deltaTime);
		stateManager.render(batch);
	}

	@Override
	public void resize(int width, int height) {
		stateManager.resize(width, height);
	}

	@Override
	public void dispose() {
		batch.dispose();
//		assetManager.dispose();
		stateManager.dispose();
		TileType.disposeTextures();
	}

	public SpriteBatch getBatch() {
		return batch;
	}

//	public AssetManager getAssetManager() {
//		return assetManager;
//	}
}

