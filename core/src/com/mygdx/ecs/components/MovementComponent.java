package com.mygdx.ecs.components;

import com.badlogic.gdx.math.Vector2;
import java.util.ArrayList;
import java.util.List;

public class MovementComponent extends Component {
    public List<Vector2> availableMoves = new ArrayList<>();
    public boolean canMove = true;
}