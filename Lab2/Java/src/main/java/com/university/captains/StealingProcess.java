package com.university.captains;

import java.util.Queue;

public class StealingProcess {
    final static int DOOR_QUEUE_SIZE = 5;
    final static int CARRIER_QUEUE_SIZE = 7;
    final static int IVANOV_DELAY = 900;
    final static int PETROV_DELAY = 1100;
    final static int NECHIPORUK_DELAY = 1500;

    Object ivanovMonitor;
    Object petrovMonitor;
    Object nechiporukMonitor;

    Queue<Weapon> weaponList;
    SyncQueue truckDoorQueue, truckCarrierQueue;
    int summaryCost;

    public StealingProcess() {
        weaponList = Utils.generateWeapons();
        truckDoorQueue = new SyncQueue(DOOR_QUEUE_SIZE);
        truckCarrierQueue = new SyncQueue(CARRIER_QUEUE_SIZE);
        ivanovMonitor = new Object();
        petrovMonitor = new Object();
        nechiporukMonitor = new Object();
        summaryCost = 0;
    }

    public void launchStealing() {

        Thread thread1 = new Thread(() -> {
            while(weaponList.size() > 0) {
                Weapon temp = weaponList.remove();
                //ivanovMonitor.wait(IVANOV_DELAY);
               // truckDoorQueue.put(temp);
                System.out.println("Ivanov brought to the truck door: *" + temp.getName() + "* weapon");
            }
        });
        thread1.start();

        Thread thread2 = new Thread(() -> {
            while(weaponList.size() > 0 || truckDoorQueue.getSize() > 0) {
                //petrovMonitor.wait(PETROV_DELAY);
                //Weapon temp = truckDoorQueue.take();
                //truckCarrierQueue.put(temp);
                //System.out.println("Petrov loaded the truck carrier with: *" + temp.getName() + "* weapon");
            }
        });
        thread2.start();

        while(weaponList.size() > 0 || truckDoorQueue.getSize() > 0 || truckCarrierQueue.getSize() > 0) {
            //nechiporukMonitor.wait(NECHIPORUK_DELAY);
            Weapon temp = truckCarrierQueue.take();
            summaryCost += temp.getCost();
            System.out.println("Nechiporuk evaluated the cost of: *" + temp.getName() + "* weapon");
        }

        System.out.println("The summary cost of all weapons is: " + summaryCost);
    }

}
