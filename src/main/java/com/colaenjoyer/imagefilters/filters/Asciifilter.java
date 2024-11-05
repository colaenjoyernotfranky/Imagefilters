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

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Asciifilter implements ImageFilter {
    private int bufferedImageWidth;
    private int bufferedImageHeight;
    private int FONT_SIZE = 8;

    public BufferedImage execute(String pathname, String mask) {
        BufferedImage inputImage = getInputImage(pathname);

        bufferedImageWidth = inputImage.getWidth();
        bufferedImageHeight = inputImage.getHeight();

        return textAsImage(toAscii(inputImage, -70));
    }

    private BufferedImage textAsImage(String textString) {
        String[] textStringAsArray = textString.split("\n");

        BufferedImage resultImage = new BufferedImage(bufferedImageWidth * FONT_SIZE, bufferedImageHeight * FONT_SIZE, BufferedImage.TYPE_INT_RGB);
        
        Graphics2D resultImageGraphics = resultImage.createGraphics();

        resultImageGraphics.setPaint(Color.WHITE);
        resultImageGraphics.fillRect(0, 0, bufferedImageWidth * FONT_SIZE, bufferedImageHeight * FONT_SIZE);

        resultImageGraphics.setColor(Color.BLACK);

        Map<TextAttribute, Object> fontAttributes = new HashMap<TextAttribute, Object>();
        fontAttributes.put(TextAttribute.TRACKING, 0);

        resultImageGraphics.setFont(new Font("Serif", Font.PLAIN, 4).deriveFont(fontAttributes));

        for(int y = 0; y < (bufferedImageHeight * FONT_SIZE); y++) {
            resultImageGraphics.drawString(textStringAsArray[y/FONT_SIZE], 0, y+1);
        }

        resultImageGraphics.dispose();
        return resultImage;
    }

    private int getPixelBrightness(BufferedImage img, int x, int y) {
        int p = img.getRGB(x, y);
        int image_r = (p >> 16) & 0xff;
        int image_g = (p >> 8) & 0xff;
        int image_b = p & 0xff;
        int pixel_brightness = (image_b + image_g + image_r) / 3;
        return pixel_brightness;
    }

    private BufferedImage getInputImage(String pathname) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(pathname));
        } catch (IOException e) {
            System.out.println(e);
        }
        return img;
    }

    private String toAscii(BufferedImage img, int offset) {
        String s = "";
        for (int i = 0; i < img.getHeight(); i++) {
            for (int j = 0; j < img.getWidth(); j++) {
                int image_brightness = getPixelBrightness(img, j, i);

                if (image_brightness <= 64 + offset) {
                    s += "██";
                } else if (64 + offset < image_brightness && image_brightness < 129 + offset) {
                    s += "▓▓";
                } else if (129 + offset < image_brightness && image_brightness < 193 + offset) {
                    s += "▒▒";
                } else {
                    s += "░░";
                }
            }
            s += "\n";
        }
        return s;
    }
}

// 78 79 32 77 65 73 68 69 78 83 63