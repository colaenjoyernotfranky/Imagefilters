package com.colaenjoyer.imagefilters.filters;

import java.awt.image.BufferedImage;

public interface ImageFilter {
    public BufferedImage execute(String pathname, String maskPath);
}
