package com.colaenjoyer.imagefilters.filters;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import com.colaenjoyer.imagefilters.configuration.FilterConfiguration;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;

@Log
@NoArgsConstructor
public class Asciifilter implements ImageFilter {
    private static final int FONT_SIZE = FilterConfiguration.AsciiFilterConfiguration.getFontSize();
    private static final int OFFSET = FilterConfiguration.AsciiFilterConfiguration.getBrightnessOffset();
    private static final Color BACKGROUND_COLOR = FilterConfiguration.AsciiFilterConfiguration.getBackgroundColor();
    private static final Color CHAR_COLOR = FilterConfiguration.AsciiFilterConfiguration.getCharColor();

    public BufferedImage execute(String pathname, String mask) {
        BufferedImage inputImage = getInputImage(pathname);

        int bufferedImageWidth = inputImage.getWidth();
        int bufferedImageHeight = inputImage.getHeight();

        BufferedImage imageMask;
        boolean[][] maskArray = null;

        if(mask != null) {
            imageMask = scaleImage(getInputImage(mask), bufferedImageWidth * FONT_SIZE,
                    bufferedImageHeight * FONT_SIZE);
            maskArray = extractMask(imageMask);
        }

        BufferedImage scaledInputImage = scaleImage(inputImage, bufferedImageWidth * FONT_SIZE,
                bufferedImageHeight * FONT_SIZE);
        return applyMask(scaledInputImage, textAsImage(toAscii(inputImage)), maskArray);
    }

    private BufferedImage scaleImage(BufferedImage originalScaleImage, int newWidth, int newHeight) {
        BufferedImage scaledImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = scaledImage.createGraphics();
        graphics2D.drawImage(originalScaleImage, 0, 0, newWidth, newHeight, null);
        return scaledImage;
    }

    private BufferedImage textAsImage(String textString) {
        String[] textStringAsArray = textString.split("\n");

        BufferedImage resultImage = new BufferedImage(textStringAsArray[0].length() * FONT_SIZE,
                textStringAsArray.length * FONT_SIZE, BufferedImage.TYPE_INT_RGB);
        
        Graphics2D resultImageGraphics = resultImage.createGraphics();

        resultImageGraphics.setPaint(BACKGROUND_COLOR);
        resultImageGraphics.fillRect(0, 0, resultImage.getWidth(), resultImage.getHeight());

        resultImageGraphics.setColor(CHAR_COLOR);

        Map<TextAttribute, Object> fontAttributes = new HashMap<>();
        fontAttributes.put(TextAttribute.TRACKING, 1);

        resultImageGraphics.setFont(new Font("Arial", Font.PLAIN, FONT_SIZE).deriveFont(fontAttributes));

        for(int y = 0; y < resultImage.getHeight()/ FONT_SIZE; y++) {
            for (int x = 0; x < resultImage.getWidth()/ FONT_SIZE; x++) {
                resultImageGraphics.drawString("" + textStringAsArray[y].charAt(x), x * FONT_SIZE, y * FONT_SIZE);
            }
        }

        resultImageGraphics.dispose();
        return resultImage;
    }

    private BufferedImage applyMask(BufferedImage image, BufferedImage filteredImage, boolean[][] mask) {
        if(mask != null) {
            for (int x = 0; x < filteredImage.getWidth(); x++) {
                for (int y = 0; y < filteredImage.getHeight(); y++) {
                    if(mask[x][y]) {
                        filteredImage.setRGB(x, y, image.getRGB(x, y));
                    }
                    if(!mask[x][y]) {
                        filteredImage.setRGB(x, y, filteredImage.getRGB(x, y));
                    }
                }
            }
        }
        return filteredImage;
    }

    private boolean[][] extractMask(BufferedImage maskImg) {
        boolean[][] mask = new boolean[maskImg.getWidth()][maskImg.getHeight()];

        for (int y = 0; y < maskImg.getHeight(); y++) {
            for (int x = 0; x < maskImg.getWidth(); x++) {
                if(maskImg.getRGB(x, y) == Color.BLACK.getRGB()) {
                    mask[x][y] =  false;
                } else if(maskImg.getRGB(x, y) == Color.WHITE.getRGB()) {
                    mask[x][y] =  true;
                }
            }
        }
        return mask;
    }

    private int getPixelBrightness(BufferedImage img, int x, int y) {
        int pixel = img.getRGB(x, y);
        int imageRed = (pixel >> 16) & 0xff;
        int imageGreen = (pixel >> 8) & 0xff;
        int imageBlue = pixel & 0xff;
        return (imageBlue + imageGreen + imageRed) / 3;
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

    private String toAscii(BufferedImage img) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < img.getHeight(); i++) {
            for (int j = 0; j < img.getWidth(); j++) {
                int imageBrightness = (getPixelBrightness(img, j, i) + Asciifilter.OFFSET);

                if (imageBrightness <= 64) {
                    s.append(".");
                } else if (imageBrightness <= 129) {
                    s.append("-");
                } else if (imageBrightness <= 193) {
                    s.append("+");
                } else {
                    s.append("*");
                }
            }
            s.append("\n");
        }
        return s.toString();
    }
}

// 78 79 32 77 65 73 68 69 78 83 63