package com.mygdx.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class MySkin {
    public static Skin createSkin() {
        Skin skin = new Skin();

        // Создаем пиксельную текстуру для фона и ползунков
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        Texture whiteTexture = new Texture(pixmap);
        pixmap.dispose();

        // Добавляем шрифт
        BitmapFont font = new BitmapFont(Gdx.files.internal("fontBArial24.fnt"));
        font.getData().setScale(1f);
        skin.add("default-font", font);

        // Создаем стили для кнопок
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = skin.getFont("default-font");
        textButtonStyle.fontColor = Color.BLACK;
        textButtonStyle.up = new TextureRegionDrawable(new TextureRegion(whiteTexture)).tint(Color.LIGHT_GRAY);
        textButtonStyle.down = new TextureRegionDrawable(new TextureRegion(whiteTexture)).tint(Color.DARK_GRAY);
        textButtonStyle.over = new TextureRegionDrawable(new TextureRegion(whiteTexture)).tint(Color.WHITE);
        skin.add("default", textButtonStyle);

        TextButton.TextButtonStyle textButtonStyle2 = new TextButton.TextButtonStyle();
        textButtonStyle2.font = skin.getFont("default-font");
        textButtonStyle2.fontColor = Color.WHITE;
        textButtonStyle2.up = new TextureRegionDrawable(new TextureRegion(whiteTexture)).tint(Color.RED);
        textButtonStyle2.down = new TextureRegionDrawable(new TextureRegion(whiteTexture)).tint(Color.valueOf("8B0000"));
        textButtonStyle2.over = new TextureRegionDrawable(new TextureRegion(whiteTexture)).tint(Color.WHITE);
        skin.add("красный-1", textButtonStyle2);

        // Создаем стиль для меток
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = skin.getFont("default-font");
        labelStyle.fontColor = Color.WHITE;
        skin.add("default", labelStyle);

//        Создаем стиль для ProgressBar
//        TextureRegionDrawable — обертка для TextureRegion.
//        tint(Color) — добавляет цвет к TextureRegionDrawable.
//        background — фон полосы.
//        knobBefore — заполненная часть полосы прогресса.
//        knob — необязательно, если вы не используете ручной ползунок.
        ProgressBar.ProgressBarStyle progressBarStyle = new ProgressBar.ProgressBarStyle();
        progressBarStyle.background = new TextureRegionDrawable(new TextureRegion(whiteTexture)).tint(Color.DARK_GRAY);
        progressBarStyle.knobBefore = new TextureRegionDrawable(new TextureRegion(whiteTexture)).tint(Color.GREEN);
        skin.add("default-horizontal", progressBarStyle);

        return skin;
    }
}
