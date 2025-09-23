package com.mygdx.core;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.states.GameStateManager;
import com.mygdx.game.states.MainMenuState;

//public class GameApplication extends Game {
//	public SpriteBatch batch;
//	public Texture img;
//	public BitmapFont font;
//
//	@Override
//	public void create () {
//		batch = new SpriteBatch();
//		font = new BitmapFont(Gdx.files.internal("fontBArial24.fnt"));
//		this.setScreen(new MainMenuState(this));
//	}
//
//	@Override
//	public void dispose () {
//		batch.dispose();
//		img.dispose();
//	}
//}

public class GameApplication extends Game {
	private SpriteBatch batch;
	private GameStateManager stateManager;
	private AssetManager assetManager;

	@Override
	public void create() {
		batch = new SpriteBatch();
		assetManager = new AssetManager();

		// Инициализация менеджера состояний
		stateManager = new GameStateManager(this);
		stateManager.pushState(new MainMenuState(stateManager));
	}

	@Override
	public void render() {
		float deltaTime = Gdx.graphics.getDeltaTime();

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