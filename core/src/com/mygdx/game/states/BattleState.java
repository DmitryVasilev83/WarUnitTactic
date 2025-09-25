package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.core.GameApplication;
import com.mygdx.game.battle.GridMap;
import com.mygdx.game.ui.MySkin;

public class BattleState extends GameState {
    private GridMap gridMap;
    private OrthographicCamera camera;
    private Viewport viewport;
    private Stage stage;
    private static final float TILE_SIZE = 40f;
    private static final int MAP_WIDTH = 12;
    private static final int MAP_HEIGHT = 10;

    public BattleState(final GameStateManager stateManager) {
        super(stateManager);

        // Создаём всё заново (не полагаемся на super для камеры)
        camera = new OrthographicCamera();
        float worldWidth = MAP_WIDTH * TILE_SIZE;
        float worldHeight = MAP_HEIGHT * TILE_SIZE;
        viewport = new FitViewport(worldWidth, worldHeight, camera);
        stage = new Stage(viewport, batch);

        gridMap = new GridMap(MAP_WIDTH, MAP_HEIGHT, TILE_SIZE);
        camera.position.set(worldWidth / 2f, worldHeight / 2f, 0);
        camera.update();

        // UI
        Table table = new Table();
        table.setFillParent(true);
        table.bottom().right().pad(20);
        stage.addActor(table);

        TextButton menuButton = new TextButton("В меню", skin, "красный-1");
        table.add(menuButton).width(120).height(40);

        menuButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                stateManager.popState(); // ← было pushState(new MainMenuState(...))
            }
        });
    }

    @Override
    public void enter() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(SpriteBatch batch) {
        Gdx.gl.glClearColor(0.2f, 0.3f, 0.2f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        gridMap.render(batch);
        batch.end();

        stage.draw();
    }

    @Override
    public void update(float deltaTime) {
        stage.act(deltaTime);
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

    public GridMap getGridMap() {
        return gridMap;
    }
}

//public class BattleState extends GameState {
//    private final GameStateManager game;
//    private OrthographicCamera camera;
//    private Viewport viewport;
//    private Stage stage;
//    private Skin skin;
//    private GridMap gridMap;
//    private static final float TILE_SIZE = 40f; // размер квадрата на карте
//    private static final int MAP_WIDTH = 12;
//    private static final int MAP_HEIGHT = 10;
//
////    private BattleManager battleManager;
////    private EntityManager entityManager;
////    private List<GameSystem> systems;
////    private UIManager uiManager;
//
//    public BattleState(final GameStateManager game) {
//        super(game);
//        this.game = game;
//
//        // Настройка камеры под размер карты
//        float worldWidth = MAP_WIDTH * TILE_SIZE;
//        float worldHeight = MAP_HEIGHT * TILE_SIZE;
//        viewport = new FitViewport(worldWidth, worldHeight, camera);
//        stage = new Stage(viewport, stateManager.getApplication().getBatch());
////      ??  stage = new Stage(viewport, batch); // обновляем stage с новым viewport
//
//        // Создаём карту MAP_WIDTHxMAP_HEIGHT
//        gridMap = new GridMap(MAP_WIDTH, MAP_HEIGHT, TILE_SIZE);
//
//        camera.position.set(worldWidth / 2f, worldHeight / 2f, 0);
//        camera.update();
//
//        Gdx.input.setInputProcessor(stage);
//
////        skin = new Skin(Gdx.files.internal("uiskin.json"));
//        skin = MySkin.createSkin();
//
////        Кнопка
//        Table table = new Table();
//        table.setFillParent(true);
//        table.bottom().right().pad(20);
//        stage.addActor(table);
//
//        // Кнопка возврата в главное меню
//        TextButton menuButton = new TextButton("В меню", skin, "красный-1");
////        menuButton.setColor(Color.RED); // просто покрасить кнопку в красный
//
//        table.add(menuButton).width(120).height(40);
//
//        menuButton.addListener(new ChangeListener() {
//            @Override
//            public void changed(ChangeEvent event, Actor actor) {
//                game.pushState(new MainMenuState(game));
//            }
//        });
//    }
//
//    @Override
//    public void render(SpriteBatch batch) {
//
//        Gdx.gl.glClearColor(0.2f, 0.3f, 0.2f, 1f);
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // заливает экран этим цветом перед отрисовкой UI
//
//        camera.update();
//        batch.setProjectionMatrix(camera.combined);
//
//        // Сначала рисуем карту
//        gridMap.render(batch);
//
//        // Потом — сущности (через RenderSystem, но пока просто заглушка)
//        // В будущем: battleManager.getRenderSystem().render(batch);
//
//        // Потом — UI
//        stage.draw();
//    }
//
//    public GridMap getGridMap() {
//        return gridMap;
//    }
//
//    @Override
//    public void update(float deltaTime) {
//
//        stage.act(deltaTime);
////        battleManager.update(deltaTime);
////
////        for (GameSystem system : systems) {
////            system.update(deltaTime);
////        }
////
////        uiManager.update(deltaTime);
//    }
//
//    @Override
//    public void enter() {Gdx.input.setInputProcessor(stage);
//    }
//
//    @Override
//    public void handleInput() {
//
//    }
//
//    @Override
//    public void exit() {
//// Ничего не нужно — текстуры управляются глобально
//    }
//
//    @Override
//    public void dispose() {
//// Не dispose'им текстуры здесь — они общие
//    }
//}



