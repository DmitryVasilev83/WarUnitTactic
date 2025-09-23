package com.mygdx.game.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.core.GameApplication;
import java.util.Stack;

public class GameStateManager{
    private GameApplication application;
    private Stack<GameState> states;

    public GameStateManager(GameApplication application) {
        this.application = application;
        this.states = new Stack<>();
    }

    public void pushState(GameState state) {
        if (!states.isEmpty()) {
            states.peek().exit();
        }
        states.push(state);
        state.enter();
    }

    public void popState() {
        if (!states.isEmpty()) {
            GameState state = states.pop();
            state.exit();
            state.dispose();

            if (!states.isEmpty()) {
                states.peek().enter();
            }
        }
    }

    public void update(float deltaTime) {
        if (!states.isEmpty()) {
            states.peek().update(deltaTime);
            states.peek().handleInput();
        }
    }

    public void render(SpriteBatch batch) {
        if (!states.isEmpty()) {
            states.peek().render(batch);
        }
    }

    public void resize(int width, int height) {
        if (!states.isEmpty()) {
            states.peek().resize(width, height);
        }
    }

    public void dispose() {
        for (GameState state : states) {
            state.dispose();
        }
        states.clear();
    }

    public GameApplication getApplication() {
        return application;
    }

    public void setScreen(MainMenuState mainMenuState) {
    }
}
