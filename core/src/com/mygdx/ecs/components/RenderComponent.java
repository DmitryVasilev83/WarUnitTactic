package com.mygdx.ecs.components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;

public class RenderComponent extends Component {
    public Texture texture;
    public float width, height;
    public Color tint;
    public int zOrder;
}
