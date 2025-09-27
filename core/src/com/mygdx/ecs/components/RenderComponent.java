package com.mygdx.ecs.components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.states.BattleState;

public class RenderComponent extends Component {
    public Texture texture;
    public float width, height;
    public Color tint;
    public int zOrder;


    public RenderComponent(Texture texture, float tileSize) {
        this.texture = texture;
        this.width = tileSize;   // ширина = размер тайла
        this.height = tileSize;  // высота = размер тайла
        this.tint = Color.WHITE;
        this.zOrder = 10; // выше тайлов (тайлы z=0)
    }
}
