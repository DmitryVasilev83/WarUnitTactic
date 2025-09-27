package com.mygdx.ecs.components;

import com.mygdx.core.GameApplication;
import com.mygdx.game.states.BattleState;

public class PositionComponent extends Component {
    public int gridX, gridY;
    public float worldX, worldY;

    public PositionComponent(int gridX, int gridY, float tileSize) {
        this.gridX = gridX;
        this.gridY = gridY;
        // Преобразуем в мировые координаты (используем TILE_SIZE из BattleState)
        this.worldX = gridX * tileSize;
        this.worldY = gridY * tileSize;
    }
}
