package com.colaenjoyer.imagefilters.filters;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.ArrayList;
import java.util.Arrays;
import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import lombok.NoArgsConstructor;
import lombok.extern.java.Log;

@Log
@NoArgsConstructor
public class Pixelsort implements ImageFilter {
    private int bufferedImageWidth;
    private int bufferedImageHeight;

    private final Random random = new Random();

    public BufferedImage execute(String pathname, String maskPath) {
        BufferedImage imageToSort = getInputImage(pathname);

        if(imageToSort != null) {
            bufferedImageWidth = imageToSort.getWidth();
            bufferedImageHeight = imageToSort.getHeight();
        } else {
            return null;
        }

        Color[][] imageColorArray = extractImageToColorArray(imageToSort);

        BufferedImage imageMask;
        boolean[][] mask = null;

        if(maskPath != null) {
            imageMask = getInputImage(maskPath);
            if(imageMask != null) {
                mask = extractMask(imageMask);
            } else {
                return null;
            }
        }

        Color[][] sortedPixels = sortPixels(imageToSort);

        addRandomColumnShifts(sortedPixels, 50);

        return applyMask(imageColorArray, sortedPixels, mask);
    }

    private BufferedImage getInputImage(String pathname) {
        BufferedImage bufferedImage = null;

        try {
            bufferedImage = ImageIO.read(new File(pathname));
        } catch (IOException e) {
            log.severe(e.getMessage());
        }
        return bufferedImage;
    }

    private int rgbAsSingleValue(int rgbValue) {
        int red = (rgbValue >> 16) & 0xff;
        int green = (rgbValue >> 8) & 0xff;
        int blue = rgbValue & 0xff;

        return ((red * 299 + green * 587 + blue * 114) / 1000);
    }

    private boolean[][] extractMask(BufferedImage maskImage) {
        boolean[][] mask = new boolean[bufferedImageWidth][bufferedImageHeight];

        for (int x = 0; x < bufferedImageWidth; x++) {
            for (int y = 0; y < bufferedImageHeight; y++) {
                if(maskImage.getRGB(x, y) == 0xFF000000) {
                    mask[x][y] =  false;
                }
                if(maskImage.getRGB(x, y) == 0xFFFFFFFF) {
                    mask[x][y] =  true;
                }
            }
        }
        return mask;
    }

    private Color[][] extractImageToColorArray(BufferedImage bufferedImage) {
        Color[][] image = new Color[bufferedImageWidth][bufferedImageHeight];

        for (int x = 0; x < bufferedImageWidth; x++) {
            for (int y = 0; y < bufferedImageHeight; y++) {
                image[x][y] = new Color(bufferedImage.getRGB(x, y));
            }
        }
        return image;
    }

    private Color[][] sortPixels(BufferedImage bufferedImage) {
        Color[][] sortedImageArray = new Color[bufferedImageWidth][bufferedImageHeight];

        for (int x = 0; x < bufferedImageWidth; x++) {
            Color[] tempArray = new Color[bufferedImageHeight];

            for (int y = 0; y < bufferedImageHeight; y++) {
                tempArray[y] = new Color(bufferedImage.getRGB(x, y));
            }

            ArrayList<Color> tempArrayAsList = new ArrayList<>(Arrays.asList(tempArray));

            tempArrayAsList.sort((o1, o2) -> rgbAsSingleValue(o1.getRGB()) - rgbAsSingleValue(o2.getRGB()));

            for (int y = 0; y < bufferedImageHeight; y++) {
                tempArray[y] = tempArrayAsList.get(y);
            }
            
            sortedImageArray[x] = tempArray;
        }
        return sortedImageArray;
    }

    private void addRandomColumnShifts(Color[][] colorArray, int maxShift) {
        Color previousPixel;
        Color temp;

        for(int x = 0; x < bufferedImageWidth; x++) {
            int n = random.nextInt(maxShift);

            for(int i = 0; i < n; i++) {
                previousPixel = colorArray[x][bufferedImageHeight-1];

                for(int y = 0; y < bufferedImageHeight; y++) {
                    temp = colorArray[x][y];
                    colorArray[x][y] = previousPixel;
                    previousPixel = temp;
                }
            }
        }
    }

    private BufferedImage applyMask(Color[][] imageArray, Color[][] sortedImageArray, boolean[][] mask) {
        BufferedImage sortedImage = new BufferedImage(bufferedImageWidth, bufferedImageHeight, BufferedImage.TYPE_INT_RGB);
        boolean useMask = mask != null;
        Color color;

        for (int x = 0; x < bufferedImageWidth; x++) {
            for (int y = 0; y < bufferedImageHeight; y++) {
                color = useMask && mask[x][y] ? imageArray[x][y] : sortedImageArray[x][y];
                sortedImage.setRGB(x, y, color.getRGB());
            }
        }
        return sortedImage;
    }
}

// 78 79 32 77 65 73 68 69 78 83 63