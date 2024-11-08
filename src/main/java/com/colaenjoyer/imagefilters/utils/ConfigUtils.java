package com.colaenjoyer.imagefilters.utils;

public class ConfigUtils {
    private ConfigUtils() {}

    public static int getEnvironmentVariable(String variableName, int defaultValue) {
        return (System.getProperty(variableName) == null || System.getProperty(variableName).isEmpty()) ?
                defaultValue : Integer.parseInt(System.getProperty(variableName));
    }

    public static String getEnvironmentVariable(String variableName, String defaultValue) {
        return (System.getProperty(variableName) == null || System.getProperty(variableName).isEmpty()) ?
                defaultValue : System.getProperty(variableName);
    }

    public static double getEnvironmentVariable(String variableName, double defaultValue) {
        return (System.getProperty(variableName) == null || System.getProperty(variableName).isEmpty()) ?
                defaultValue : Double.parseDouble(System.getProperty(variableName));
    }
}
