package com.university.captains;

import java.util.Queue;

public class StealingProcess {
    final static int DOOR_QUEUE_SIZE = 2;
    final static int CARRIER_QUEUE_SIZE = 3;
    final static int IVANOV_DELAY = 700;
    final static int PETROV_DELAY = 1100;
    final static int NECHIPORUK_DELAY = 1500;

    Queue<Weapon> weaponList;
    SyncQueue truckDoorQueue, truckCarrierQueue;
    int summaryCost;

    public StealingProcess() {
        weaponList = Utils.generateWeapons();
        Object temp = new Object();
        truckDoorQueue = new SyncQueue(DOOR_QUEUE_SIZE, temp);
        truckCarrierQueue = new SyncQueue(CARRIER_QUEUE_SIZE, temp);
        summaryCost = 0;
    }

    public void launchStealing() throws InterruptedException {

        Thread thread1 = new Thread(() -> {
            while (weaponList.size() > 0) {
                Weapon temp = weaponList.remove();
                try {
                    Thread.sleep(IVANOV_DELAY);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                truckDoorQueue.put(temp);
                System.out.println("Ivanov brought to the truck door: *" + temp.getName() + "* weapon");
            }

            System.out.println("IVANOV HAS FINISHED HIS STEALING!!!");
        });
        thread1.start();

        Thread thread2 = new Thread(() -> {
            while (weaponList.size() > 0 || truckDoorQueue.getSize() > 0) {
                Weapon temp = truckDoorQueue.take();
                try {
                    Thread.sleep(PETROV_DELAY);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                truckCarrierQueue.put(temp);
                System.out.println("Petrov loaded the truck carrier with: *" + temp.getName() + "* weapon");
            }

            System.out.println("PETROV HAS FINISHED HIS STEALING!!!");
        });
        thread2.start();

        while (weaponList.size() > 0 || truckDoorQueue.getSize() > 0 || truckCarrierQueue.getSize() > 0) {
            Weapon temp = truckCarrierQueue.take();
            Thread.sleep(NECHIPORUK_DELAY);
            summaryCost += temp.getCost();
            System.out.println("Nechiporuk evaluated the cost of: *" + temp.getName() + "* weapon");
        }

        System.out.println("NECHIPORUK HAS FINISHED HIS STEALING!!!");
        System.out.println();
        System.out.println("The summary cost of all weapons is: " + summaryCost);
    }

}
