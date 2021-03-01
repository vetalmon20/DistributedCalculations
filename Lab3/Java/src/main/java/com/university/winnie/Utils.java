package com.university.winnie;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Utils {
    public static List<Bee> createBeeList(int length, Pot pot, Semaphore potSemaphore, Bear bear) {
        List<Bee> beeList = new ArrayList<>();
        for(int i = 0; i < length; i++) {
            beeList.add(new Bee(pot, potSemaphore, i, bear));
        }
        return beeList;
    }

    public static void startEatingProcess(List<Bee> beeList, int length, Bear bear, Semaphore semaphore) throws InterruptedException {

        semaphore.acquire();
        new Thread(bear).start();
        for(int i = 0; i < length; i++) {
            new Thread(beeList.get(i)).start();
        }
    }
}
