package com.university.shell;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ShellUtils {
    public static List<String> lineToWords(String line) {
        if(line == null) {
            throw new NullPointerException("Wrong text line in shell");
        }
        line = line.replaceAll(System.lineSeparator(), "");
        line = line.trim().replaceAll(" +", " ");
        return new ArrayList<>(Arrays.asList(line.split(" ")));
    }
}
