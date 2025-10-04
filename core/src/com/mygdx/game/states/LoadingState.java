package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.core.GameApplication;

public class LoadingState extends GameState {
    private Stage stage;
    private ProgressBar progressBar;
    private Label loadingLabel;

    public LoadingState(GameStateManager stateManager) {
        super(stateManager);
    }

    @Override
    public void enter() {
        stage = new Stage(new ScreenViewport(), batch);

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        loadingLabel = new Label("Загрузка...", skin);
        progressBar = new ProgressBar(0, 1, 0.01f, false, skin);

        table.add(loadingLabel).row();
        table.add(progressBar).width(300).height(20);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void update(float deltaTime) {
        AssetManager assetManager = ((GameApplication) Gdx.app.getApplicationListener()).getAssetManager();

        assetManager.update();

        float progress = assetManager.getProgress();
        progressBar.setValue(progress);

        if (progress >= 1) {
            // Загрузка завершена — переходим на главное меню
            stateManager.popState(); // Убираем LoadingState
            stateManager.pushState(new MainMenuState(stateManager)); // Переходим в главное меню
        }

        stage.act(deltaTime);
    }

    @Override
    public void render(SpriteBatch batch) {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.draw();
    }

    @Override
    public void handleInput() {}

    @Override
    public void exit() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
