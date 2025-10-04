package com.mygdx.game.states;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.game.ui.MySkin;

public abstract class GameState {
    protected GameStateManager stateManager;
    protected SpriteBatch batch;
    protected Skin skin;

    public GameState(GameStateManager stateManager) {
        this.stateManager = stateManager;
        this.batch = stateManager.getApplication().getBatch();
        this.skin = MySkin.createSkin();
    }

    public abstract void enter();
    public abstract void update(float deltaTime);
    public abstract void render(SpriteBatch batch);
    public abstract void handleInput();
    public abstract void exit();
    public abstract void dispose();

    public void resize(int width, int height) {
        // будет переопределено в подклассах, если нужно
    }
}










