package com.colaenjoyer.imagefilters.configuration;

public final class FilterConfiguration {
    private FilterConfiguration() {}

    public static final class AsciiFilterConfiguration {
        private static final int FONT_SIZE = getEnvironmentVariable("FONT_SIZE", 8);
        private static final int BRIGHTNESS_OFFSET = getEnvironmentVariable("BRIGHTNESS_OFFSET", 0);

        private AsciiFilterConfiguration() {}

        public static int getFontSize() {
            return FONT_SIZE;
        }

        public static int getBrightnessOffset() {
            return BRIGHTNESS_OFFSET;
        }
    }

    public static final class PixelsortConfiguration {
        private static final int MAX_PIXEL_SHIFT = getEnvironmentVariable("MAX_PIXEL_SHIFT", 50);

        private PixelsortConfiguration() {}

        public static int getMaxPixelShift() {
            return MAX_PIXEL_SHIFT;
        }
    }

    private static int getEnvironmentVariable(String variableName, int defaultValue) {
        return (System.getProperty(variableName) == null || System.getProperty(variableName).isEmpty()) ?
                defaultValue : Integer.parseInt(System.getProperty(variableName));
    }

    private static String getEnvironmentVariable(String variableName, String defaultValue) {
        return (System.getProperty(variableName) == null || System.getProperty(variableName).isEmpty()) ?
                defaultValue : System.getProperty(variableName);
    }

    private static double getEnvironmentVariable(String variableName, double defaultValue) {
        return (System.getProperty(variableName) == null || System.getProperty(variableName).isEmpty()) ?
                defaultValue : Double.parseDouble(System.getProperty(variableName));
    }
}
