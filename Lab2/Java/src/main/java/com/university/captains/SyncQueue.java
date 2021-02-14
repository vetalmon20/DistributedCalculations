package com.university.captains;

import java.util.LinkedList;
import java.util.Queue;

public class SyncQueue {
    private final Queue<Weapon> queue;
    private final int maxSize;

    //private final Object notFull;
    public final Object notEmpty;

    public SyncQueue(int maxSize) {
        this.queue = new LinkedList<>();
        this.maxSize = maxSize;

        //this.notFull = new Object();
        this.notEmpty = new Object();
    }

    public synchronized void put(Weapon weapon) {
        while(queue.size() == maxSize) {
            try {
                //notFull.wait();
                notEmpty.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        queue.add(weapon);
        notEmpty.notifyAll();
    }

    public synchronized Weapon take(){
        while(queue.size() == 0) {
            try {
                notEmpty.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Weapon temp = queue.remove();
        //notFull.notifyAll();
        notEmpty.notifyAll();

        return temp;
    }

    public int getSize() {
        return queue.size();
    }
}
