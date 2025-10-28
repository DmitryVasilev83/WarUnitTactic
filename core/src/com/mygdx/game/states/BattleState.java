package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.core.GameApplication;
import com.mygdx.ecs.entities.Entity;
import com.mygdx.ecs.entities.EntityManager;
import com.mygdx.ecs.entities.UnitFactory;
import com.mygdx.ecs.systems.RenderSystem;
import com.mygdx.ecs.entities.UnitSpawner;
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
    // поля для управления камерой
    private Vector3 lastTouchPosition = new Vector3();
    private boolean isDragging = false;
    private float dragThreshold = 5f; // Порог для определения перетаскивания
    private float moveSpeed = 8f; // Скорость движения камеры по клавишам
    private float mouseBorderThreshold = 5f; // Порог для движения по краю экрана
    private float minCameraX, maxCameraX, minCameraY, maxCameraY; // Границы камеры
    // флаги для отслеживания нажатий клавиш
    private boolean wPressed = false;
    private boolean sPressed = false;
    private boolean aPressed = false;
    private boolean dPressed = false;

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

        // Центрируем камеру
        camera.setToOrtho(false, worldWidth, worldHeight);
        camera.position.set(worldWidth / 2f, worldHeight / 2f, 0);
        // Установим начальный зум
        currentZoom = 1.0f;
        camera.zoom = currentZoom;
        camera.update();

        // Вычисляем границы камеры
        calculateCameraBounds(worldWidth, worldHeight);

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

    // Обработка движения камеры по клавишам
    private void handleCameraMovement(float deltaTime) {
        float moveAmount = moveSpeed * tileSize * deltaTime * camera.zoom;

        boolean moved = false;
        float newX = camera.position.x;
        float newY = camera.position.y;

        // Проверяем наши внутренние флаги
        if (wPressed || Gdx.input.isKeyPressed(Input.Keys.UP)) {
            newY += moveAmount;
            moved = true;
        }
        if (sPressed || Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            newY -= moveAmount;
            moved = true;
        }
        if (aPressed || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            newX -= moveAmount;
            moved = true;
        }
        if (dPressed || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            newX += moveAmount;
            moved = true;
        }

        // Обработка движения камеры по краю экрана
        if (!moved) {
            int mouseX = Gdx.input.getX();
            int mouseY = Gdx.input.getY();
            int screenWidth = Gdx.graphics.getWidth();
            int screenHeight = Gdx.graphics.getHeight();

            if (mouseX < mouseBorderThreshold) {
                newX -= moveAmount;
                moved = true;
            } else if (mouseX > screenWidth - mouseBorderThreshold) {
                newX += moveAmount;
                moved = true;
            }
            // В LibGDX: Y=0 — внизу, Y=height — вверху
            if (mouseY > screenHeight - mouseBorderThreshold) { // Верх экрана (мышка вверху)
                newY += moveAmount; // Двигаем камеру вверх → увеличиваем Y
                moved = true;
            } else if (mouseY < mouseBorderThreshold) { // Низ экрана (мышка внизу)
                newY -= moveAmount; // Двигаем камеру вниз → уменьшаем Y
                moved = true;
            }
        }

        if (moved) {
            // Ограничиваем движение камеры границами мира
            camera.position.x = Math.max(minCameraX, Math.min(maxCameraX, newX));
            camera.position.y = Math.max(minCameraY, Math.min(maxCameraY, newY));
            camera.update();
        }
    }

    // Метод для вычисления границ камеры
    private void calculateCameraBounds(float worldWidth, float worldHeight) {
        // Учитываем текущий зум при вычислении границ
        float halfViewportWidth = camera.viewportWidth * 0.5f * camera.zoom;
        float halfViewportHeight = camera.viewportHeight * 0.5f * camera.zoom;

        minCameraX = halfViewportWidth;
        maxCameraX = worldWidth - halfViewportWidth;
        minCameraY = halfViewportHeight;
        maxCameraY = worldHeight - halfViewportHeight;
    }

    // новое - вспомогательный метод для масштабирования
    private void zoomCamera(float factor) {
        float newZoom = camera.zoom * factor;
        newZoom = Math.max(minZoom, Math.min(maxZoom, newZoom));
        camera.zoom = newZoom;

        // Пересчитываем границы камеры при изменении зума
        float worldWidth = gridMap.getWidth() * tileSize;
        float worldHeight = gridMap.getHeight() * tileSize;
        calculateCameraBounds(worldWidth, worldHeight);

        // Ограничиваем позицию камеры новыми границами
        camera.position.x = Math.max(minCameraX, Math.min(maxCameraX, camera.position.x));
        camera.position.y = Math.max(minCameraY, Math.min(maxCameraY, camera.position.y));

        camera.update();
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
    public void enter() {
        // Используем InputMultiplexer, чтобы и stage, и BattleState обрабатывали ввод
        InputMultiplexer multiplexer = new InputMultiplexer();

        // Сначала добавляем BattleState, чтобы она могла перехватывать ввод раньше stage
        multiplexer.addProcessor(this);
        multiplexer.addProcessor(stage);

        Gdx.input.setInputProcessor(multiplexer);
    }

    @Override
    public void update(float deltaTime) {
        // Обработка движения камеры по клавишам
        handleCameraMovement(deltaTime);
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
        // Не dispose batch, так как он управляется приложением
    }

    @Override
    public boolean keyDown(int keycode) {
        // Обработка клавиш для масштабирования
        if (keycode == Input.Keys.PLUS || keycode == Input.Keys.EQUALS) {
            zoomCamera(0.9f);
            return false; // возвращаем false, чтобы stage тоже мог обработать
        } else if (keycode == Input.Keys.MINUS) {
            zoomCamera(1.1f);
            return false; // возвращаем false, чтобы stage тоже мог обработать
        }

        // Устанавливаем флаги для движения камеры
        switch (keycode) {
            case Input.Keys.W:
                wPressed = true;
                break;
            case Input.Keys.S:
                sPressed = true;
                break;
            case Input.Keys.A:
                aPressed = true;
                break;
            case Input.Keys.D:
                dPressed = true;
                break;
        }

        return false; // возвращаем false, чтобы stage тоже мог обработать
    }

    @Override
    public boolean keyUp(int keycode) {
        // Сбрасываем флаги для движения камеры
        switch (keycode) {
            case Input.Keys.W:
                wPressed = false;
                break;
            case Input.Keys.S:
                sPressed = false;
                break;
            case Input.Keys.A:
                aPressed = false;
                break;
            case Input.Keys.D:
                dPressed = false;
                break;
        }

        return false; // возвращаем false, чтобы stage тоже мог обработать
    }

    @Override
    public boolean keyTyped(char character) {
        return false; // возвращаем false, чтобы stage тоже мог обработать
    }


    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.LEFT) {
            // Преобразуем координаты экрана в координаты мира
            Vector3 touchPos = new Vector3(screenX, screenY, 0);
            camera.unproject(touchPos);
            lastTouchPosition.set(touchPos);
            isDragging = true;
            return false; // возвращаем false, чтобы stage тоже мог обработать
        }
        return false; // возвращаем false, чтобы stage тоже мог обработать
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.LEFT) {
            isDragging = false;
        }
        return false; // возвращаем false, чтобы stage тоже мог обработать
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (isDragging) {
            // Преобразуем новые координаты экрана в координаты мира
            Vector3 touchPos = new Vector3(screenX, screenY, 0);
            camera.unproject(touchPos);

            // Вычисляем разницу между старой и новой позицией
            float deltaX = lastTouchPosition.x - touchPos.x;
            float deltaY = lastTouchPosition.y - touchPos.y;

            // Перемещаем камеру
            camera.position.x += deltaX;
            camera.position.y += deltaY;

            // Ограничиваем движение камеры границами мира
            camera.position.x = Math.max(minCameraX, Math.min(maxCameraX, camera.position.x));
            camera.position.y = Math.max(minCameraY, Math.min(maxCameraY, camera.position.y));

            camera.update();

            // Обновляем последнюю позицию
            lastTouchPosition.set(touchPos);
            return false; // возвращаем false, чтобы stage тоже мог обработать
        }
        return false; // возвращаем false, чтобы stage тоже мог обработать
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        // Не нужно обрабатывать, так как движение камеры по краю экрана уже обрабатывается в update
        return false; // возвращаем false, чтобы stage тоже мог обработать
    }

    @Override
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

        // Пересчитываем границы камеры при изменении зума
        float worldWidth = gridMap.getWidth() * tileSize;
        float worldHeight = gridMap.getHeight() * tileSize;
        calculateCameraBounds(worldWidth, worldHeight);

        // Ограничиваем позицию камеры новыми границами
        camera.position.x = Math.max(minCameraX, Math.min(maxCameraX, camera.position.x));
        camera.position.y = Math.max(minCameraY, Math.min(maxCameraY, camera.position.y));

        camera.update();

        return false; // возвращаем false, чтобы stage тоже мог обработать
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        uiViewport.update(width, height, true); // <-- обновляем UI viewport
        camera.update();

        // Пересчитываем границы камеры при изменении размера экрана
        float worldWidth = gridMap.getWidth() * tileSize;
        float worldHeight = gridMap.getHeight() * tileSize;
        calculateCameraBounds(worldWidth, worldHeight);
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






