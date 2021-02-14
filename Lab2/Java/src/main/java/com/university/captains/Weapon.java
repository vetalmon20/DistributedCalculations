package com.university.captains;

public class Weapon {
    private final String name;
    private final int cost;

    public Weapon(String name, int cost) {
        this.name = name;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }
}
