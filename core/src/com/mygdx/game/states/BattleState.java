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
import com.mygdx.game.ui.MySkin;

public class BattleState extends GameState {
    private final GameStateManager game;
    private OrthographicCamera camera;
    private Viewport viewport;
    private Stage stage;
    private Skin skin;
//    private GridMap gridMap;
    private static final float TILE_SIZE = 64f; // размер квадрата на карте

//    private BattleManager battleManager;
//    private EntityManager entityManager;
//    private List<GameSystem> systems;
//    private UIManager uiManager;

    public BattleState(final GameStateManager game) {
        super(game);
        this.game = game;

        camera = new OrthographicCamera();
        viewport = new FitViewport(800, 600, camera);
        stage = new Stage(viewport, stateManager.getApplication().getBatch());

        // Создаём карту 10x10
//        gridMap = new GridMap(10, 10, TILE_SIZE);

        Gdx.input.setInputProcessor(stage);

//        skin = new Skin(Gdx.files.internal("uiskin.json"));
        skin = MySkin.createSkin();

        Table table = new Table();
        table.setFillParent(true);
        table.bottom().right().pad(20);
        stage.addActor(table);

        // Кнопка возврата в главное меню
        TextButton menuButton = new TextButton("В меню", skin, "красный-1");
//        menuButton.setColor(Color.RED); // просто покрасить кнопку в красный

        table.add(menuButton).width(120).height(40);

        menuButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.pushState(new MainMenuState(game));
            }
        });
    }

    @Override
    public void render(SpriteBatch batch) {

//        Gdx.gl.glClearColor(0f, 0.7f, 0f, 1f); // устанавливает цвет очистки экрана. Слегка зеленый
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // заливает экран этим цветом перед отрисовкой UI

        Gdx.gl.glClearColor(0.2f, 0.3f, 0.2f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        // Сначала рисуем карту
//        gridMap.render(batch);

        // Потом — сущности (через RenderSystem, но пока просто заглушка)
        // В будущем: battleManager.getRenderSystem().render(batch);

        // Потом — UI
        stage.draw();
    }

//    public GridMap getGridMap() {
//        return gridMap;
//    }

    @Override
    public void update(float deltaTime) {

        stage.act(deltaTime);
//        battleManager.update(deltaTime);
//
//        for (GameSystem system : systems) {
//            system.update(deltaTime);
//        }
//
//        uiManager.update(deltaTime);
    }

    @Override
    public void enter() {Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void handleInput() {

    }

    @Override
    public void exit() {

    }

    @Override
    public void dispose() {

    }
}



