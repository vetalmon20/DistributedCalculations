package com.university.captains;

import java.util.LinkedList;
import java.util.Queue;

public class SyncQueue {
    private final Queue<Weapon> queue;
    private final int maxSize;

    public final Object monitor;

    public SyncQueue(int maxSize, Object monitor) {
        this.queue = new LinkedList<>();
        this.maxSize = maxSize;

        this.monitor = monitor;

    }

    public void put(Weapon weapon) {
        synchronized (monitor) {
            while (queue.size() == maxSize) {
                try {
                    monitor.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            queue.add(weapon);
            monitor.notifyAll();
        }
    }

    public Weapon take() {
        synchronized (monitor) {
            while (queue.size() == 0) {
                try {
                    monitor.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Weapon temp = queue.remove();
            monitor.notifyAll();

            return temp;
        }
    }

    public int getSize() {
        return queue.size();
    }
}
