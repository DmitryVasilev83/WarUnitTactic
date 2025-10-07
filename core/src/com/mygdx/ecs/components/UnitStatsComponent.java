package com.mygdx.ecs.components;

import com.mygdx.game.data.units.Team;
import com.mygdx.game.data.units.UnitData;

public class UnitStatsComponent extends Component {
    public int health, maxHealth;
    public int actionPoints, maxActionPoints;
    public int movementRange;
    public int initiative;
    public Team team;
    public String unitId; // Вместо UnitType

    public UnitStatsComponent(UnitData data, Team team) {
        this.unitId = data.id;
        this.team = team;
        this.maxHealth = data.baseHealth;
        this.health = maxHealth;
        this.maxActionPoints = data.actionPoints;
        this.actionPoints = maxActionPoints;
        this.movementRange = data.movement;
        this.initiative = data.initiative;
    }
}
