package com.mygdx.ecs.entities;

//public class UnitFactory {
//    public static Entity createUnit(UnitType type, Team team, int x, int y) {
//        Entity unit = new Entity();
//
//        unit.addComponent(new PositionComponent(x, y));
//        unit.addComponent(new RenderComponent(getTextureForType(type)));
//        unit.addComponent(new UnitStatsComponent(type, team));
//        unit.addComponent(new SelectableComponent());
//        unit.addComponent(new MovementComponent());
//        unit.addComponent(new CombatComponent(type));
//
//        if (team == Team.AI) {
//            unit.addComponent(new AIComponent());
//        }
//
//        return unit;
//    }
//}
