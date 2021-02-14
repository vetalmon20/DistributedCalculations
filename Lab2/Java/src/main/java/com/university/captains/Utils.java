package com.university.captains;

import java.util.LinkedList;
import java.util.Queue;

public class Utils {
    public static Queue<Weapon> generateWeapons() {
        Queue<Weapon> weaponList = new LinkedList<>();

        weaponList.add(new Weapon("Desert eagle", 700));
        weaponList.add(new Weapon("P2000", 200));
        weaponList.add(new Weapon("P250", 300));
        weaponList.add(new Weapon("Dual berrettas", 400));
        weaponList.add(new Weapon("Five-SeveN", 500));
        weaponList.add(new Weapon("Smoke Grenade", 300));
        weaponList.add(new Weapon("HE Grenade", 300));
        weaponList.add(new Weapon("Flashbang", 200));
        weaponList.add(new Weapon("Decoy Grenade", 50));
        weaponList.add(new Weapon("Molotov", 600));
        weaponList.add(new Weapon("Incendiary Grenade", 600));
        weaponList.add(new Weapon("Galil AR", 2000));
        weaponList.add(new Weapon("AK-47", 2700));
        weaponList.add(new Weapon("SSG 08", 2000));
        weaponList.add(new Weapon("SG 553", 3000));
        weaponList.add(new Weapon("AWP", 4750));
        weaponList.add(new Weapon("G3SG1", 5000));
        weaponList.add(new Weapon("Zeus x27", 1000));
        weaponList.add(new Weapon("MAC-10", 1200));
        weaponList.add(new Weapon("MP7", 1200));
        weaponList.add(new Weapon("UMP-45", 1600));
        weaponList.add(new Weapon("P950", 2500));
        weaponList.add(new Weapon("Bison", 1400));

        return weaponList;
    }
}
