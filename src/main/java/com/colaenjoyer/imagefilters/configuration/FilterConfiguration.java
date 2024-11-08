package com.colaenjoyer.imagefilters.configuration;

import com.colaenjoyer.imagefilters.utils.ConfigUtils;

public final class FilterConfiguration {
    private FilterConfiguration() {}

    public static final class AsciiFilterConfiguration {
        private static final int FONT_SIZE = ConfigUtils.getEnvironmentVariable("FONT_SIZE", 8);
        private static final int BRIGHTNESS_OFFSET = ConfigUtils.getEnvironmentVariable("BRIGHTNESS_OFFSET", 0);

        private AsciiFilterConfiguration() {}

        public static int getFontSize() {
            return FONT_SIZE;
        }

        public static int getBrightnessOffset() {
            return BRIGHTNESS_OFFSET;
        }
    }

    public static final class PixelsortConfiguration {
        private static final int MAX_PIXEL_SHIFT = ConfigUtils.getEnvironmentVariable("MAX_PIXEL_SHIFT", 50);

        private PixelsortConfiguration() {}

        public static int getMaxPixelShift() {
            return MAX_PIXEL_SHIFT;
        }
    }
}
