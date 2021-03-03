package com.university.readers;

public class Main {

    public static final String path = "src/main/java/com/university/readers/phones.txt";

    public static void main(String[] args) {

        PhonesManager phonesManager = new PhonesManager(path);

        Utils.startProcess(phonesManager);

    }
}
