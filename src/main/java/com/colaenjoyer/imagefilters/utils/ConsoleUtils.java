package com.colaenjoyer.imagefilters.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import com.colaenjoyer.imagefilters.configuration.AppConfiguration;
import com.colaenjoyer.imagefilters.filters.Asciifilter;
import com.colaenjoyer.imagefilters.filters.ImageFilter;
import com.colaenjoyer.imagefilters.filters.Pixelsort;
import lombok.extern.java.Log;

@Log
public class ConsoleUtils {
    private ConsoleUtils() {}

    private static final Scanner in = new Scanner(System.in);
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private static void removeChars(int amount) {
        for(int i = 0; i <= amount; i++) {
            System.out.print("\b");
        }
    }

    public static char selectionMenu() {
        boolean validSelection = false;
        char selection = 0;
        while (!validSelection) {
            System.out.print("\nSelection: ");
            selection = in.next().charAt(0);
            if(selection == 'q' || selection == 'a' || selection == 'p') {
                validSelection = true;
            }
        }
        return selection;
    }

    public static SelectionResult executeSelection(char selection) {
        SelectionResult result;
        switch (selection) {
            case 'a' -> result = new SelectionResult(false, new Asciifilter());
            case 'p' -> result = new SelectionResult(false, new Pixelsort());
            case 'q' -> result = new SelectionResult(true, null);
            default -> result = new SelectionResult(false, null);
        }
        return result;
    }

    public static InputImagePaths getImagePaths() {
        System.out.print("\nImage path: ");
        String imagePath = in.next().replace("\"", "");

        System.out.print("Mask path: ");
        String maskPath = in.next().replace("\"", "");

        if(maskPath.isEmpty()) {
            maskPath = null;
        }
        return new InputImagePaths(imagePath, maskPath);
    }

    public static void saveResultImage(BufferedImage resultImage, String imagePath, ImageFilter selectedImageFilter) {
        String outName = null;
        if(getOperatingSystem().toLowerCase().contains("windows")) {
            outName = AppConfiguration.getOutputPath() + "\\" + imagePath.substring(imagePath.lastIndexOf("\\") + 1, imagePath.lastIndexOf("."));
        }
        if(getOperatingSystem().toLowerCase().contains("linux")) {
            outName = AppConfiguration.getOutputPath() + "/" + imagePath.substring(imagePath.lastIndexOf("/") + 1, imagePath.lastIndexOf("."));
        }
        try {
            if(selectedImageFilter.getClass() == Asciifilter.class) {
                ImageIO.write(resultImage, "png", new File(outName + "_ascii.png"));
                clearScreen();
                log.info("Saved ascii image to " + outName + "_ascii.png");
            }
            if(selectedImageFilter.getClass() == Pixelsort.class) {
                ImageIO.write(resultImage, "png", new File(outName + "_sorted.png"));
                clearScreen();
                log.info("Saved sorted image to " + outName + "_sorted.png");
            }
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e) {
            log.severe(e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    public static String getOperatingSystem() {
        return System.getProperty("os.name");
    }
}
