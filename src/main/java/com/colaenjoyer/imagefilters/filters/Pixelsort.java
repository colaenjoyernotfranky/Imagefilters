package com.colaenjoyer.imagefilters.filters;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
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

    private Random random = new Random();

    public BufferedImage execute(String pathname, String maskPath) {
        BufferedImage imageToSort = getInputImage(pathname);

        if(imageToSort != null) {
            bufferedImageWidth = imageToSort.getWidth();
            bufferedImageHeight = imageToSort.getHeight();
        } else {
            return null;
        }

        Color[][] imgArray = extractImg(imageToSort);

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

        Color[][] sortedPixels = sort(imageToSort);

        addRandomColumnShifts(sortedPixels, 50);

        return applyMask(imgArray, sortedPixels, mask);
    }

    private BufferedImage getInputImage(String pathname) {
        BufferedImage img = null;

        try {
            img = ImageIO.read(new File(pathname));
        } catch (IOException e) {
            log.severe(e.getMessage());
        }
        return img;
    }

    private int rgbAsSingleValue(int rgbValue) {
        int r = (rgbValue >> 16) & 0xff;
        int g = (rgbValue >> 8) & 0xff;
        int b = rgbValue & 0xff;

        return ((r * 299 + g * 587 + b * 114) / 1000);
    }

    private boolean[][] extractMask(BufferedImage maskImg) {
        boolean[][] mask = new boolean[bufferedImageWidth][bufferedImageHeight];

        for (int x = 0; x < bufferedImageWidth; x++) {
            for (int y = 0; y < bufferedImageHeight; y++) {
                if(maskImg.getRGB(x, y) == 0xFF000000) {
                    mask[x][y] =  false;
                }
                if(maskImg.getRGB(x, y) == 0xFFFFFFFF) {
                    mask[x][y] =  true;
                }
            }
        }
        return mask;
    }

    private Color[][] extractImg(BufferedImage bufferedImage) {
        Color[][] img = new Color[bufferedImageWidth][bufferedImageHeight];

        for (int x = 0; x < bufferedImageWidth; x++) {
            for (int y = 0; y < bufferedImageHeight; y++) {
                img[x][y] = new Color(bufferedImage.getRGB(x, y));
            }
        }
        return img;
    }

    private Color[][] sort(BufferedImage bufferedImage) {
        Color[][] sortedImgArray = new Color[bufferedImageWidth][bufferedImageHeight];

        for (int x = 0; x < bufferedImageWidth; x++) {
            Color[] tempArray = new Color[bufferedImageHeight];

            for (int y = 0; y < bufferedImageHeight; y++) {
                tempArray[y] = new Color(bufferedImage.getRGB(x, y));
            }

            ArrayList<Color> tempArrayAsList = new ArrayList<>(Arrays.asList(tempArray));

            Collections.sort(tempArrayAsList, (o1, o2) -> rgbAsSingleValue(o1.getRGB()) - rgbAsSingleValue(o2.getRGB()));

            for (int y = 0; y < bufferedImageHeight; y++) {
                tempArray[y] = tempArrayAsList.get(y);
            }
            
            sortedImgArray[x] = tempArray;
        }
        return sortedImgArray;
    }

    private void addRandomColumnShifts(Color[][] colorArray, int maxShift) {
        Color previous;
        Color temp;

        for(int x = 0; x < bufferedImageWidth; x++) {
            int n = random.nextInt(maxShift);

            for(int i = 0; i < n; i++) {
                previous = colorArray[x][bufferedImageHeight-1];

                for(int y = 0; y < bufferedImageHeight; y++) {
                    temp = colorArray[x][y];
                    colorArray[x][y] = previous;
                    previous = temp;
                }
            }
        }
    }

    //TODO: Reduce complexity
    private BufferedImage applyMask(Color[][] imageArray, Color[][] sortedImageArray, boolean[][] mask) {
        BufferedImage sortedImage = new BufferedImage(bufferedImageWidth, bufferedImageHeight, BufferedImage.TYPE_INT_RGB);

        if(mask == null) {
            for (int x = 0; x < bufferedImageWidth; x++) {
                for (int y = 0; y < bufferedImageHeight; y++) {
                    sortedImage.setRGB(x, y, sortedImageArray[x][y].getRGB());
                }
            }
            return sortedImage;
        } else {
            for (int x = 0; x < bufferedImageWidth; x++) {
                for (int y = 0; y < bufferedImageHeight; y++) {
                    if(mask[x][y]) {
                        sortedImage.setRGB(x, y, imageArray[x][y].getRGB());
                    }
                    if(!mask[x][y]) {
                        sortedImage.setRGB(x, y, sortedImageArray[x][y].getRGB());
                    }
                }
            }
            return sortedImage;
        }
    }
}

// 78 79 32 77 65 73 68 69 78 83 63