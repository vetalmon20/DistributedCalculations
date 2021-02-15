package com.university.monasteries;

import java.util.List;
import java.util.concurrent.RecursiveTask;

public class BattleArena extends RecursiveTask<Monk> {
    List<Monk> monkList;
    int leftBorder, rightBorder;

    public BattleArena(List<Monk> monkList, int leftBorder, int rightBorder) {
        this.monkList = monkList;
        this.leftBorder = leftBorder;
        this.rightBorder = rightBorder;
    }

    private Monk defineBattleWinner(Monk firstFighter, Monk secondFighter) {
        return firstFighter.getcEnergy() >= secondFighter.getcEnergy() ? firstFighter : secondFighter;
    }

    @Override
    protected Monk compute() {
        if(rightBorder - leftBorder == 1) {
            System.out.println("The battle between: " + monkList.get(leftBorder).getMonasteryName()
                    + " " + monkList.get(leftBorder).getcEnergy()
                    + " and " + monkList.get(rightBorder).getMonasteryName()
                    + " " + monkList.get(rightBorder).getcEnergy());
            try {
                Thread.sleep(700);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return defineBattleWinner(monkList.get(leftBorder), monkList.get(rightBorder));
        } else {
            int middle = (rightBorder + leftBorder) / 2;
            BattleArena leftArena = new BattleArena(monkList, leftBorder, middle);
            BattleArena rightArena = new BattleArena(monkList, middle, rightBorder);

            leftArena.fork();
            rightArena.fork();

            Monk leftWinner = leftArena.join();
            Monk rightWinner = rightArena.join();

            System.out.println("The battle between: " + leftWinner.getMonasteryName() + " " + leftWinner.getcEnergy()
                    + " and " + rightWinner.getMonasteryName() + " " + rightWinner.getcEnergy());
            return defineBattleWinner(leftWinner, rightWinner);
        }
    }
}
