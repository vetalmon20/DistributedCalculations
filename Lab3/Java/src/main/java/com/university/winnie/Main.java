package com.university.winnie;

import java.util.List;
import java.util.concurrent.Semaphore;

public class Main {
    public static final int BEES_NUM = 5;
    public static final int POT_SIZE = 53;

    public static void main(String[] args) throws InterruptedException {

        Semaphore potSemaphore = new Semaphore(1);
        Pot pot = new Pot(POT_SIZE);

        Bear bear = new Bear(pot, potSemaphore);

        List<Bee> beeList = Utils.createBeeList(BEES_NUM, pot, potSemaphore, bear);

        Utils.startEatingProcess(beeList, BEES_NUM, bear, potSemaphore);

    }
}
