package com.mygdx.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class MySkin {
    public static Skin createSkin() {
        Skin skin = new Skin();

        // Создаем пиксельную текстуру для фона кнопок
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add("white", new Texture(pixmap));
        pixmap.dispose();

        // Добавляем шрифт
        BitmapFont font = new BitmapFont(Gdx.files.internal("fontBArial24.fnt"));
        font.getData().setScale(1f);  // Увеличиваем размер шрифта
        skin.add("default-font", font);

        // Создаем стили для кнопок
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = skin.getFont("default-font");
        textButtonStyle.fontColor = Color.BLACK;
        textButtonStyle.up = skin.newDrawable("white", Color.LIGHT_GRAY); // фон кнопки в обычном состоянии
        textButtonStyle.down = skin.newDrawable("white", Color.DARK_GRAY); // цвет кнопки при нажатии
        textButtonStyle.over = skin.newDrawable("white", Color.WHITE); // цвет кнопки при наведении
        skin.add("default", textButtonStyle);

        TextButton.TextButtonStyle textButtonStyle2 = new TextButton.TextButtonStyle();
        textButtonStyle2.font = skin.getFont("default-font");
        textButtonStyle2.fontColor = Color.WHITE;
        textButtonStyle2.up = skin.newDrawable("white", Color.RED); // фон кнопки в обычном состоянии
        textButtonStyle2.down = skin.newDrawable("white", Color.valueOf("8B0000")); // при нажатии
        textButtonStyle2.over = skin.newDrawable("white", Color.WHITE); // цвет кнопки при наведении
        skin.add("красный-1", textButtonStyle2);

        // Создаем стили для меток
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = skin.getFont("default-font");
        labelStyle.fontColor = Color.WHITE;
        skin.add("default", labelStyle);

        return skin;
    }
}
