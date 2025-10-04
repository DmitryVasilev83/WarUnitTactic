package com.mygdx.ecs.components;

import com.mygdx.game.data.AbilityType;
import com.mygdx.game.data.UnitData;
import com.mygdx.game.data.UnitType;

import java.util.Arrays;
import java.util.List;

public class CombatComponent extends Component {
    public int attackDamage;
    public int attackRange;
    public List<AbilityType> abilities;

    public CombatComponent(UnitData data) {
        this.attackDamage = 10; // можно тоже из JSON
        this.attackRange = data.attackRange;
        this.abilities = Arrays.asList(AbilityType.BASIC_ATTACK);
    }
}
