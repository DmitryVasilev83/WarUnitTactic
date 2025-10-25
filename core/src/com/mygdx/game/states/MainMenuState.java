package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.core.GameApplication;


public class MainMenuState extends GameState {
    private Table table;
    private Stage uiStage;
    private TextButton startButton, settingsButton, exitButton;

    public MainMenuState(GameStateManager stateManager) {
        super(stateManager);

        uiStage = new Stage(new ScreenViewport(), batch);

        table = new Table();
        table.setFillParent(true);
        uiStage.addActor(table);
        createUI();
    }

    private void createUI() {
        TextButton settingsButton = new TextButton("Настройки битвы", skin);
        settingsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GameState setupState = new BattleSetupState(stateManager);
                stateManager.pushState(setupState);
            }
        });

        TextButton startButton = new TextButton("Старт", skin);
        startButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // Проверяем, загрузились ли ресурсы
                AssetManager assetManager = ((GameApplication) Gdx.app.getApplicationListener()).getAssetManager();

                if (assetManager.isFinished()) {
                    GameState battleState = new BattleState(stateManager);
                    stateManager.pushState(battleState);
                } else {
                    System.out.println("Ресурсы ещё не загружены!");
                }
            }
        });

        exitButton = new TextButton("Выход", skin);
        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });

        table.add(settingsButton).padBottom(20).width(200).height(60);
        table.row();
        table.add(startButton).width(200).height(60);
        table.row();
        table.add(exitButton).padTop(20).width(200).height(60);
    }

    @Override
    public void enter() {
        Gdx.input.setInputProcessor(uiStage);
    }

    @Override
    public void update(float deltaTime) {
        uiStage.act(deltaTime);
    }

    @Override
    public void render(SpriteBatch batch) {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        uiStage.draw();
    }

    @Override
    public void handleInput() {
        // Обработка дополнительного ввода
    }

    @Override
    public void exit() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        uiStage.dispose();
    }
}