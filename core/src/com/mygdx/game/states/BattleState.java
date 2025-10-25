package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.core.GameApplication;
import com.mygdx.ecs.entities.Entity;
import com.mygdx.ecs.entities.EntityManager;
import com.mygdx.ecs.entities.UnitFactory;
import com.mygdx.ecs.systems.RenderSystem;
import com.mygdx.game.battle.UnitSpawner;
import com.mygdx.game.battle.map.GridMap;
import com.mygdx.game.data.MapLoader;
import com.mygdx.game.data.units.Team;
import com.mygdx.game.data.units.UnitData;

public class BattleState extends GameState implements InputProcessor {
    private GridMap gridMap;
    private TiledMap tiledMap; // Новое поле
    private OrthographicCamera camera;
    private Viewport viewport;
    private Stage stage;
    private EntityManager entityManager;
    private RenderSystem renderSystem;
    private GameApplication app;
    private float tileSize;
    private UnitSpawner unitSpawner;
    private float minZoom = 0.2f;
    private float maxZoom = 2.0f;
    private float currentZoom;
    private Viewport uiViewport;

    public BattleState(final GameStateManager stateManager) {
        super(stateManager);
        this.app = (GameApplication) Gdx.app.getApplicationListener();

        AssetManager assetManager = app.getAssetManager();

        if (!assetManager.isFinished()) {
            assetManager.finishLoading();
        }

        MapLoader.MapData mapData = MapLoader.loadMap("maps/test.tmx", assetManager);
        gridMap = mapData.gridMap;
        this.tiledMap = mapData.tiledMap;
        this.tileSize = mapData.tileWidth;

        int width = gridMap.getWidth();
        int height = gridMap.getHeight();
        float worldWidth = width * tileSize;
        float worldHeight = height * tileSize;

        // Создаём камеру
        camera = new OrthographicCamera();

        // Используем ExtendViewport для масштабирования под размер экрана
        // ExtendViewport вместо FitViewport — он масштабирует мир, но не растягивает.
        viewport = new ExtendViewport(worldWidth, worldHeight, camera);
//        stage = new Stage(viewport, app.getBatch());

        // Центрируем камеру
//        camera.setToOrtho(false, Gdx.graphics.getWidth() / 32f, Gdx.graphics.getHeight() / 32f);
        camera.setToOrtho(false, worldWidth, worldHeight);
        camera.position.set(worldWidth / 2f, worldHeight / 2f, 0);
        // Установим начальный зум
        currentZoom = 1.0f;
        camera.zoom = currentZoom;
        camera.update();

        // UI viewport — всегда на весь экран
        uiViewport = new ScreenViewport(); // <-- UI не масштабируется
        stage = new Stage(uiViewport, app.getBatch());

        entityManager = new EntityManager();
        renderSystem = new RenderSystem(entityManager, app.getBatch());

        // Создаём юнитов из карты
        System.out.println("Creating units from map...");
        for (UnitData unitData : mapData.units) {
            System.out.println("Creating unit: " + unitData.id + " at tile (" +
                    unitData.startX + ", " + unitData.startY + ")");

            Entity unitEntity = UnitFactory.createUnitFromData(unitData, tileSize, app, tiledMap);
            if (unitEntity != null) {
                entityManager.addEntity(unitEntity);
            }
        }

        // Создаем юнитов вручную
        unitSpawner = new UnitSpawner(entityManager, tileSize);
        Entity warrior2 = unitSpawner.spawnUnit("warrior", Team.PLAYER, 14, 10, "unitspng/warrior.png");
        Entity mage2 = unitSpawner.spawnUnit("mage", Team.PLAYER, 14, 9, "unitspng/mage.png");

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

        stage.draw(); // <-- Stage использует свой собственный viewport
    }

    @Override
    public void update(float deltaTime) {stage.act(deltaTime);}

    @Override
    public void enter() {
        // Используем InputMultiplexer, чтобы и stage, и BattleState обрабатывали ввод
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(this);
        Gdx.input.setInputProcessor(multiplexer);
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

    public boolean keyDown(int keycode) {
        return false;
    }

    public boolean keyUp(int keycode) {
        return false;
    }


    public boolean keyTyped(char character) {
        return false;
    }


    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }


    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }


    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }


    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }


    public boolean scrolled(float amountX, float amountY) {
        float newZoom = camera.zoom;
        if (amountY > 0) {
            newZoom *= 1.1f; // Уменьшаем зум (отдаляем)
        } else if (amountY < 0) {
            newZoom /= 1.1f; // Увеличиваем зум (приближаем)
        }

        // Ограничиваем зум
        newZoom = Math.max(minZoom, Math.min(maxZoom, newZoom));
        camera.zoom = newZoom;
        camera.update();

        return true;
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        uiViewport.update(width, height, true); // <-- обновляем UI viewport
        camera.update();
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

    public TiledMap getTiledMap() {
        return tiledMap;
    }
}






