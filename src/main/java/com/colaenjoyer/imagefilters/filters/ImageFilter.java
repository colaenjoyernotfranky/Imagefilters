package com.colaenjoyer.imagefilters.filters;

import java.awt.image.BufferedImage;

public interface ImageFilter {
    BufferedImage execute(String pathname, String maskPath);
}
