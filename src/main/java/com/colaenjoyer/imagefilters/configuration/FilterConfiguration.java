package com.colaenjoyer.imagefilters.configuration;

import com.colaenjoyer.imagefilters.utils.ConfigUtils;

import java.awt.*;

public final class FilterConfiguration {
    private FilterConfiguration() {}

    public static final class AsciiFilterConfiguration {
        private static final int FONT_SIZE = ConfigUtils.getEnvironmentVariable("FONT_SIZE", 8);
        private static final String FONT = ConfigUtils.getEnvironmentVariable("FONT", "Serif");
        private static final int BRIGHTNESS_OFFSET = ConfigUtils.getEnvironmentVariable("BRIGHTNESS_OFFSET", 0);
        private static final Color BACKGROUND_COLOR = ConfigUtils.getEnvironmentVariable("BACKGROUND_COLOR", Color.BLACK);
        private static final Color CHAR_COLOR = ConfigUtils.getEnvironmentVariable("CHAR_COLOR", Color.WHITE);

        private AsciiFilterConfiguration() {}

        public static int getFontSize() {
            return FONT_SIZE;
        }

        public static String getFont() {
            return FONT;
        }

        public static int getBrightnessOffset() {
            return BRIGHTNESS_OFFSET;
        }

        public static Color getBackgroundColor() {
            return BACKGROUND_COLOR;
        }

        public static Color getCharColor() {
            return CHAR_COLOR;
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
