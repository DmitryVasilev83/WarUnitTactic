package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
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
import com.mygdx.core.GameApplication;
import com.mygdx.ecs.entities.Entity;
import com.mygdx.ecs.entities.EntityManager;
import com.mygdx.ecs.entities.UnitFactory;
import com.mygdx.ecs.systems.RenderSystem;
import com.mygdx.game.battle.map.GridMap;
import com.mygdx.game.data.MapLoader;
import com.mygdx.game.data.units.Team;
import com.mygdx.game.data.units.UnitData;

public class BattleState extends GameState {
    private GridMap gridMap;
    private OrthographicCamera camera;
    private Viewport viewport;
    private Stage stage;
    private EntityManager entityManager;
    private RenderSystem renderSystem;
    private GameApplication app;
    private float tileSize; // Храним размер тайла из карты

    public BattleState(final GameStateManager stateManager) {
        super(stateManager);
        this.app = (GameApplication) Gdx.app.getApplicationListener();

        AssetManager assetManager = app.getAssetManager();

        if (!assetManager.isFinished()) {
            assetManager.finishLoading();
        }

        // Загружаем карту
        MapLoader.MapData mapData = MapLoader.loadMap("maps/test.tmx", assetManager);
        gridMap = mapData.gridMap;
        this.tileSize = mapData.tileWidth; // Используем размер тайла из карты

        System.out.println("=== MAP LOADING DEBUG ===");
        System.out.println("Units loaded from map: " + mapData.units.length);
        System.out.println("Tile size from map: " + tileSize);

        int width = gridMap.getWidth();
        int height = gridMap.getHeight();
        float worldWidth = width * tileSize;
        float worldHeight = height * tileSize;

        camera = new OrthographicCamera();
        viewport = new FitViewport(worldWidth, worldHeight, camera);
        stage = new Stage(viewport, app.getBatch()); // Используем batch из приложения

        camera.position.set(worldWidth / 2f, worldHeight / 2f, 0);
        camera.update();

        // === ИНИЦИАЛИЗАЦИЯ ECS ===
        entityManager = new EntityManager();
        renderSystem = new RenderSystem(entityManager, app.getBatch()); // Используем batch из приложения

        // Создаём юнитов из карты
        System.out.println("Creating units...");
        for (UnitData unitData : mapData.units) {
            // Преобразуем тайловые координаты в мировые
            float worldX = unitData.startX * tileSize;
            float worldY = unitData.startY * tileSize;

            System.out.println("Creating unit: " + unitData.id + " at world coords (" + worldX + ", " + worldY + ")");
            Entity unitEntity = UnitFactory.createUnit(unitData.id, unitData.team, worldX, worldY, tileSize, app);
            if (unitEntity != null) {
                entityManager.addEntity(unitEntity);
            }
        }

        System.out.println("Entities in EntityManager: " + entityManager.getEntities().size());

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
                stateManager.popState();
            }
        });
    }

    @Override
    public void render(SpriteBatch batch) {
        Gdx.gl.glClearColor(0.2f, 0.3f, 0.2f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin(); // Начинаем batch перед рисованием
        gridMap.render(batch);
        renderSystem.render();
        batch.end(); // Заканчиваем batch

        stage.draw();
    }

    @Override
    public void update(float deltaTime) {
        stage.act(deltaTime);
    }

    @Override
    public void enter() {
        Gdx.input.setInputProcessor(stage);
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
        // Не dispose batch, так как он управляется приложением
    }

    public GridMap getGridMap() {
        return gridMap;
    }

    public float getTileSize() {
        return tileSize;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }
}







