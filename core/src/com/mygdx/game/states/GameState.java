package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.ui.MySkin;

public abstract class GameState {
    protected GameStateManager stateManager;
    protected SpriteBatch batch;
    protected Stage stage;
    protected Viewport viewport;
    protected OrthographicCamera camera;
    protected Skin skin;

    public GameState(GameStateManager stateManager) {
        this.stateManager = stateManager;
        this.batch = stateManager.getApplication().getBatch();

        camera = new OrthographicCamera();
        viewport = new FitViewport(800, 600, camera);
        stage = new Stage(viewport, batch);
//        skin = new Skin(Gdx.files.internal("uiskin.json"));
        skin = MySkin.createSkin();
    }

    public abstract void enter();
    public abstract void update(float deltaTime);
    public abstract void render(SpriteBatch batch);
    public abstract void handleInput();
    public abstract void exit();
    public abstract void dispose();

    public void resize(int width, int height) {
        viewport.update(width, height);
    }
}





