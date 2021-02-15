package com.university.monasteries;

import java.util.List;
import java.util.concurrent.ForkJoinPool;

public class Main {
    public static final int ARENAS_NUMBER = 4;
    public static void main(String[] args) {
        List<Monk> monkList = Utils.generateMonks();

        Utils.printMonkList(monkList);

        ForkJoinPool pool = new ForkJoinPool(ARENAS_NUMBER);
        Monk winner = pool.invoke(new BattleArena(monkList, 0, monkList.size() - 1));
        System.out.println();
        System.out.println("The winner of the battle is: " + winner.getMonasteryName() + " " + winner.getcEnergy());
    }
}
