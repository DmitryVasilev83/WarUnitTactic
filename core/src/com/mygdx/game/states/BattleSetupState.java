package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.core.GameApplication;
import com.mygdx.game.ui.MySkin;

public class BattleSetupState extends GameState {
    private final GameStateManager game;
    private OrthographicCamera camera;
    private Viewport viewport;
    private Stage stage;
    private Skin skin;
//    private BattleConfiguration config;
//    private Stage setupUI;

    public BattleSetupState(final GameStateManager game) {
        super(game);
        this.game = game;

        camera = new OrthographicCamera();
        viewport = new FitViewport(800, 600, camera);
        stage = new Stage(viewport, stateManager.getApplication().getBatch());

        Gdx.input.setInputProcessor(stage);

//        skin = new Skin(Gdx.files.internal("uiskin.json"));
        skin = MySkin.createSkin();

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        // Заголовок экрана настроек
        Label titleLabel = new Label("Настройки битвы", skin);
        titleLabel.setFontScale(2);

        // Заглушка для настроек
        Label placeholderLabel = new Label("Здесь будут настройки (заглушка)", skin);

        // Кнопка возврата в главное меню
        TextButton backButton = new TextButton("Назад", skin);

        table.add(titleLabel).padBottom(50);
        table.row();
        table.add(placeholderLabel).padBottom(50);
        table.row();
        table.add(backButton).width(200).height(60);

        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.pushState(new MainMenuState(game));
            }
        });
    }

    @Override
    public void render(SpriteBatch batch) {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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
    public void handleInput() {

    }

    @Override
    public void exit() {

    }

       @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
