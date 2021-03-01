package com.university.winnie;

import java.util.concurrent.Semaphore;

public class Bee implements Runnable{
    private final int beeNum;
    private final Semaphore semaphore;
    private final Pot pot;
    private boolean isLast;
    private final Bear bear;

    public Bee(Pot pot, Semaphore semaphore, int beeNum, Bear bear) {
        this.pot = pot;
        this.semaphore = semaphore;
        this.beeNum = beeNum;
        this.isLast = false;
        this.bear = bear;
    }


    @Override
    public void run() {
        while(true) {
            while(semaphore.availablePermits() == 0 && !pot.isFull()) {
                System.out.println("Bee №" + beeNum + " bring a sip of honey to the pot");
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                isLast = pot.incrSize();
            }
            if(isLast) {
                System.out.println("Bee №" + beeNum + " filled the pot and waking up the Bear");
                bear.awakeBear();
            }
            isLast = false;
        }

    }
}
