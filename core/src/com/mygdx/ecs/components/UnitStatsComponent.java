package com.mygdx.ecs.components;

import com.mygdx.game.data.Team;
import com.mygdx.game.data.UnitType;

public class UnitStatsComponent extends Component {
    public int health, maxHealth;
    public int actionPoints, maxActionPoints;
    public int movementRange;
    public int initiative;
    public Team team;
    public UnitType unitType;

    public UnitStatsComponent(UnitType type, Team team) {
        this.unitType = type;
        this.team = team;
        this.maxHealth = type.baseHealth;
        this.health = maxHealth;
        this.maxActionPoints = type.actionPoints;
        this.actionPoints = maxActionPoints;
        this.movementRange = type.movement;
        this.initiative = type.initiative;
    }
}
