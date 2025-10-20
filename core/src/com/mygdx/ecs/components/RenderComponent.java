package com.mygdx.ecs.components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTile;


public class RenderComponent extends Component {
    public Texture texture;
    public TextureRegion textureRegion;
    public TiledMapTile tiledMapTile; // Новое поле для тайла из Tiled
    public float width, height;
    public Color tint;
    public int zOrder;

    // Конструктор для обычной текстуры
    public RenderComponent(Texture texture, float tileSize) {
        this.texture = texture;
        this.textureRegion = null;
        this.tiledMapTile = null;
        this.width = tileSize;
        this.height = tileSize;
        this.tint = Color.WHITE;
        this.zOrder = 10;
    }

    // Новый конструктор для тайла из Tiled
    public RenderComponent(TiledMapTile tiledMapTile, float tileSize) {
        this.tiledMapTile = tiledMapTile;
        this.textureRegion = tiledMapTile != null ? tiledMapTile.getTextureRegion() : null;
        this.texture = null;
        this.width = tileSize;
        this.height = tileSize;
        this.tint = Color.WHITE;
        this.zOrder = 10;
    }

    // Конструктор для TextureRegion
    public RenderComponent(TextureRegion textureRegion, float tileSize) {
        this.textureRegion = textureRegion;
        this.tiledMapTile = null;
        this.texture = null;
        this.width = tileSize;
        this.height = tileSize;
        this.tint = Color.WHITE;
        this.zOrder = 10;
    }

    public void render(SpriteBatch batch, float x, float y) {
        if (tiledMapTile != null && tiledMapTile.getTextureRegion() != null) {
            // Используем тайл из Tiled
            batch.draw(tiledMapTile.getTextureRegion(), x, y, width, height);
        } else if (textureRegion != null) {
            // Используем TextureRegion
            batch.draw(textureRegion, x, y, width, height);
        } else if (texture != null) {
            // Используем Texture
            batch.draw(texture, x, y, width, height);
        }
    }

    public boolean hasTiledMapTile() {
        return tiledMapTile != null;
    }

    public boolean hasTextureRegion() {
        return textureRegion != null;
    }

    public boolean hasTexture() {
        return texture != null;
    }
}


