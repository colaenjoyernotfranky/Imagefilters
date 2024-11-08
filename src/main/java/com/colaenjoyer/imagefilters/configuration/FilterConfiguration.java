package com.colaenjoyer.imagefilters.configuration;

public class FilterConfiguration {
    private FilterConfiguration() {}

    public static class AsciiFilterConfiguration {
        //TODO: Get from config file
        private static final int FONT_SIZE = 8;
        private static final int BRIGHTNESS_OFFSET = 0;
        private static final int MAX_PIXEL_SHIFT = 50;

        private AsciiFilterConfiguration() {}

        public static int getFontSize() {
            return FONT_SIZE;
        }

        public static int getBrightnessOffset() {
            return BRIGHTNESS_OFFSET;
        }
    }

    public static class PixelsortConfiguration {

        private PixelsortConfiguration() {}

        public static int getMaxPixelShift() {
            return AsciiFilterConfiguration.MAX_PIXEL_SHIFT;
        }
    }
}
