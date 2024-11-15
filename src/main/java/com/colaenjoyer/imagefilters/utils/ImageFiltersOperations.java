package com.colaenjoyer.imagefilters.utils;

import com.colaenjoyer.imagefilters.configuration.AppConfiguration;
import com.colaenjoyer.imagefilters.filters.Asciifilter;
import com.colaenjoyer.imagefilters.filters.ImageFilter;
import com.colaenjoyer.imagefilters.filters.Pixelsort;
import lombok.extern.java.Log;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import static com.colaenjoyer.imagefilters.utils.ConsoleUtils.clearScreen;
import static com.colaenjoyer.imagefilters.utils.ConsoleUtils.getOperatingSystem;

@Log
public class ImageFiltersOperations {
    private static final Scanner in = new Scanner(System.in);

    private ImageFiltersOperations() {}

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

    public static void executeSelection(char selection) {
        ImageFilter selectedImageFilter = null;
        switch (selection) {
            case 'a': selectedImageFilter = new Asciifilter(); break;
            case 'p': selectedImageFilter = new Pixelsort(); break;
            default: case 'q': System.exit(0); break;
        }
        InputImagePaths inputImagePaths = getImagePaths();
        BufferedImage filterResult = selectedImageFilter.execute(inputImagePaths.imagePath(), inputImagePaths.maskPath());
        if(filterResult != null) {
            saveResultImage(filterResult, inputImagePaths.imagePath(), selectedImageFilter);
        } else {
            log.severe("filterResult is null. Check your image paths.");
        }
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
        String separator = getOperatingSystem().toLowerCase().contains("windows") ? "\\" : "/";
        String outName = AppConfiguration.getOutputPath() + separator + imagePath.substring(
                (imagePath.lastIndexOf(separator) + 1), imagePath.lastIndexOf("."));
        try {
            clearScreen();
            outName += selectedImageFilter.getClass() == Asciifilter.class ? "_ascii.png" : "_sorted.png";
            ImageIO.write(resultImage, "png", new File(outName));
            log.info("Saved image to " + outName);
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e) {
            log.severe(e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}
