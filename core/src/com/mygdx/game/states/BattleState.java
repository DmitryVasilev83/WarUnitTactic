package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.ecs.entities.EntityManager;
import com.mygdx.ecs.entities.UnitFactory;
import com.mygdx.ecs.systems.RenderSystem;
import com.mygdx.game.battle.GridMap;
import com.mygdx.game.data.Team;
import com.mygdx.game.data.UnitType;

public class BattleState extends GameState {
    private GridMap gridMap;
    private OrthographicCamera camera;
    private Viewport viewport;
    private Stage stage;
    private static final float TILE_SIZE = 40f;
    private static final int MAP_WIDTH = 12;
    private static final int MAP_HEIGHT = 10;
    private EntityManager entityManager;
    private RenderSystem renderSystem;


//    private List<GameSystem> systems;
//    private UIManager uiManager;


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

        // === ИНИЦИАЛИЗАЦИЯ ECS ===
        entityManager = new EntityManager();
        renderSystem = new RenderSystem(entityManager, batch);

        // Создаём тестовых юнитов
        spawnTestUnits();

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

    private void spawnTestUnits() {
        float tileSize = gridMap.getTileSize();
        // Игрок
        entityManager.addEntity(UnitFactory.createUnit(UnitType.WARRIOR, Team.PLAYER, 2, 2, tileSize));
        entityManager.addEntity(UnitFactory.createUnit(UnitType.ARCHER,  Team.PLAYER, 3, 2, tileSize));

        // Враг
        entityManager.addEntity(UnitFactory.createUnit(UnitType.MAGE,    Team.AI,     8, 7, tileSize));
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
        renderSystem.render();
        batch.end();

        stage.draw();
    }

    @Override
    public void update(float deltaTime) {

        stage.act(deltaTime);

//        @Override
//        public void update(float deltaTime) {
//            battleManager.update(deltaTime);
//
//            for (GameSystem system : systems) {
//                system.update(deltaTime);
//            }
//
//            uiManager.update(deltaTime);
//        }
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





