package com.mygdx.ecs.components;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.List;

public class MovementComponent extends Component {
    public List<Vector2> availableMoves;
    public boolean canMove;
}
