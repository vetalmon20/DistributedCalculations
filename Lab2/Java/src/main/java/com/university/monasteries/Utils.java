package com.university.monasteries;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Utils {
    public static final String inMonastery = "GUAN-IN";
    public static final String yanMonastery = "GUAN-YAN";
    public static final int maxCEnergy = 99;

    public static List<Monk> generateMonks() {
        List<Monk> monkList = new ArrayList<>();
        Random random = new Random();

        monkList.add(new Monk(inMonastery, random.nextInt(maxCEnergy) + 1));
        monkList.add(new Monk(yanMonastery, random.nextInt(maxCEnergy) + 1));
        monkList.add(new Monk(inMonastery, random.nextInt(maxCEnergy) + 1));
        monkList.add(new Monk(yanMonastery, random.nextInt(maxCEnergy) + 1));
        monkList.add(new Monk(inMonastery, random.nextInt(maxCEnergy) + 1));
        monkList.add(new Monk(yanMonastery, random.nextInt(maxCEnergy) + 1));
        monkList.add(new Monk(inMonastery, random.nextInt(maxCEnergy) + 1));
        monkList.add(new Monk(yanMonastery, random.nextInt(maxCEnergy) + 1));
        monkList.add(new Monk(inMonastery, random.nextInt(maxCEnergy) + 1));
        monkList.add(new Monk(yanMonastery, random.nextInt(maxCEnergy) + 1));
        monkList.add(new Monk(yanMonastery, random.nextInt(maxCEnergy) + 1));
        monkList.add(new Monk(yanMonastery, random.nextInt(maxCEnergy) + 1));
        monkList.add(new Monk(yanMonastery, random.nextInt(maxCEnergy) + 1));
        monkList.add(new Monk(inMonastery, random.nextInt(maxCEnergy) + 1));
        monkList.add(new Monk(inMonastery, random.nextInt(maxCEnergy) + 1));
        monkList.add(new Monk(yanMonastery, random.nextInt(maxCEnergy) + 1));

        return monkList;
    }

    public static void printMonkList(List<Monk> monkList) {
        System.out.println("The participants of the battle:");
        for (Monk monk : monkList) {
            System.out.println(monk.getMonasteryName() + " " + monk.getcEnergy());
        }
    }
}
