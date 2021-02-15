package com.university.monasteries;

public class Monk {
    private String monasteryName;
    private int cEnergy;

    public Monk(String monasteryName, int cEnergy) {
        this.monasteryName = monasteryName;
        this.cEnergy = cEnergy;
    }

    public String getMonasteryName() {
        return monasteryName;
    }

    public int getcEnergy() {
        return cEnergy;
    }
}
