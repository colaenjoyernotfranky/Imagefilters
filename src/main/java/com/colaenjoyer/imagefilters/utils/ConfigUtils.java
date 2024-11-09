package com.colaenjoyer.imagefilters.utils;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ConfigUtils {
    private ConfigUtils() {}

    public static int getEnvironmentVariable(String variableName, int defaultValue) {
        return (System.getenv(variableName) == null || System.getenv(variableName).isEmpty()) ?
                defaultValue : Integer.parseInt(System.getenv(variableName));
    }

    public static String getEnvironmentVariable(String variableName, String defaultValue) {
        return (System.getenv(variableName) == null || System.getenv(variableName).isEmpty()) ?
                defaultValue : System.getenv(variableName);
    }

    public static double getEnvironmentVariable(String variableName, double defaultValue) {
        return (System.getenv(variableName) == null || System.getenv(variableName).isEmpty()) ?
                defaultValue : Double.parseDouble(System.getenv(variableName));
    }

    public static Color getEnvironmentVariable(String variableName, Color defaultValue) {
        return (System.getenv(variableName) == null || System.getenv(variableName).isEmpty()) ?
                defaultValue : stringColorMap.get(System.getenv(variableName));
    }

    private static final Map<String, Color> stringColorMap;
    static {
        stringColorMap = new HashMap<>();
        stringColorMap.put("BLACK", Color.BLACK);
        stringColorMap.put("RED", Color.RED);
        stringColorMap.put("GREEN", Color.GREEN);
        stringColorMap.put("BLUE", Color.BLUE);
        stringColorMap.put("YELLOW", Color.YELLOW);
        stringColorMap.put("WHITE", Color.WHITE);
        stringColorMap.put("MAGENTA", Color.MAGENTA);
        stringColorMap.put("CYAN", Color.CYAN);
        stringColorMap.put("DARK_GRAY", Color.DARK_GRAY);
        stringColorMap.put("GRAY", Color.GRAY);
        stringColorMap.put("PINK", Color.PINK);
        stringColorMap.put("LIGHT_GRAY", Color.LIGHT_GRAY);
        stringColorMap.put("ORANGE", Color.ORANGE);
    }
}
