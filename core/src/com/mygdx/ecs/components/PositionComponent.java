package com.mygdx.ecs.components;

public class PositionComponent extends Component {
    public float gridX, gridY;
    public float worldX, worldY;

    // Конструктор для создания из тайловых координат
    public PositionComponent(float gridX, float gridY, float tileSize) {
        this.gridX = gridX;
        this.gridY = gridY;
        this.worldX = gridX * tileSize;
        this.worldY = gridY * tileSize;
    }

    // Новый конструктор для создания из мировых координат (из TMX)
    public PositionComponent(float worldX, float worldY) {
        this.worldX = worldX;
        this.worldY = worldY;
        // gridX и gridY можно вычислить, если нужно
        // this.gridX = worldX / tileSize; // tileSize нужно передать или хранить
        this.gridX = 0; // временно, если не нужно
        this.gridY = 0;
    }

    // Конструктор по умолчанию
    public PositionComponent() {
        this.gridX = 0;
        this.gridY = 0;
        this.worldX = 0;
        this.worldY = 0;
    }
}
