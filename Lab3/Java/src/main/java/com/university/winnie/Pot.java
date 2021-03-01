package com.university.winnie;

public class Pot {
    private int size;
    private int capacity;
    private Object monitor;

    public Pot(int capacity) {
        this.size = 0;
        this.capacity = capacity;
        this.monitor = new Object();
    }

    public synchronized boolean isFull() {
        return size == capacity;
    }

    public synchronized boolean incrSize() {
        if(!isFull()) {
            this.size++;
            if(this.size == this.capacity) {
                return true;
            }
            return false;
        }

        return false;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
