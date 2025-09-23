package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.core.GameApplication;
import com.mygdx.game.ui.MySkin;

//public class MainMenuState implements Screen {
//    private final GameApplication game;
//    private OrthographicCamera camera;
//    private Viewport viewport;
//    private Stage stage;
//    private Skin skin;

//    public MainMenuState(final GameApplication game) {
//        this.game = game;
//
//        camera = new OrthographicCamera();
//        viewport = new FitViewport(800, 600, camera);
//        stage = new Stage(viewport, game.batch);
//
//        Gdx.input.setInputProcessor(stage);
//
//        // Загружаем скин для UI элементов
////        Не работает с uiskin!!! поэтому сделал свой скин
////        skin = new Skin(Gdx.files.internal("uiskin.json"));
//        skin = MySkin.createSkin();
//
//        // Создаем таблицу для размещения кнопок
//        Table table = new Table();
//        table.setFillParent(true);
//        stage.addActor(table);
//
//        // Создаем кнопки
//        TextButton settingsButton = new TextButton("Настройки битвы", skin);
//        TextButton startButton = new TextButton("Старт", skin);
//
//        // Добавляем кнопки в таблицу
//        table.add(settingsButton).padBottom(20).width(200).height(60);
//        table.row();
//        table.add(startButton).width(200).height(60);
//
//        // Обработчики нажатий на кнопки
//        settingsButton.addListener(new ChangeListener() {
//            @Override
//            public void changed(ChangeEvent event, Actor actor) {
//                game.setScreen(new BattleSetupState(game));
//
//            }
//        });
//
//        startButton.addListener(new ChangeListener() {
//            @Override
//            public void changed(ChangeEvent event, Actor actor) {
//                game.setScreen(new BattleState(game));
//
//            }
//        });
//    }
//
//    @Override
//    public void show() {
//    }
//
//    @Override
//    public void render(float delta) {
//        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//
//        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
//        stage.draw();
//    }
//
//    @Override
//    public void resize(int width, int height) {
//        viewport.update(width, height);
//    }
//
//    @Override
//    public void pause() {
//    }
//
//    @Override
//    public void resume() {
//    }
//
//    @Override
//    public void hide() {
//    }
//
//    @Override
//    public void dispose() {
//        stage.dispose();
//        skin.dispose();
//    }
//}

public class MainMenuState extends GameState {
    private Table table;

    public MainMenuState(GameStateManager stateManager) {
        super(stateManager);

        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        createUI();
    }

    private void createUI() {
        TextButton settingsButton = new TextButton("Настройки битвы", skin);
        TextButton startButton = new TextButton("Старт", skin);

        settingsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GameState setupState = new BattleSetupState(stateManager);
                stateManager.pushState(setupState);
            }
        });

        startButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GameState battleState = new BattleState(stateManager);
                stateManager.pushState(battleState);
            }
        });

        table.add(settingsButton).padBottom(20).width(200).height(60);
        table.row();
        table.add(startButton).width(200).height(60);
    }

    @Override
    public void enter() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void update(float deltaTime) {
        stage.act(deltaTime);
    }

      @Override
    public void render(SpriteBatch batch) {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.draw();
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
        stage.dispose();
    }
}