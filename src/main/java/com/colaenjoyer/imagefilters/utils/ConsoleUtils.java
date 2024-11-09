package com.colaenjoyer.imagefilters.utils;

import lombok.extern.java.Log;

@Log
public class ConsoleUtils {
    private ConsoleUtils() {}

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static String getOperatingSystem() {
        return System.getProperty("os.name");
    }
}