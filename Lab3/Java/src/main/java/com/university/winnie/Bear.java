package com.university.winnie;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;

public class Bear implements Runnable{
    private final Pot pot;
    private final Semaphore semaphore;
    private final AtomicBoolean isAwaken;

    public Bear(Pot pot, Semaphore semaphore) {
        this.semaphore = semaphore;
        this.pot = pot;
        this.isAwaken = new AtomicBoolean(false);
    }

    public synchronized void awakeBear() {
        if(isAwaken.get()) {
            return;
        }
        isAwaken.set(true);
        semaphore.release();
    }

    @Override
    public void run() {
        while(true) {
            while(semaphore.availablePermits() == 1) {
                try {
                    System.out.println("Bear is eating the honey...");
                    Thread.sleep(1500);
                    pot.setSize(0);
                    System.out.println("Bear ate the honey");
                    isAwaken.set(false);
                    semaphore.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

