package com.colaenjoyer.imagefilters.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Scanner;

import javax.imageio.ImageIO;

import com.colaenjoyer.imagefilters.filters.Asciifilter;
import com.colaenjoyer.imagefilters.filters.Pixelsort;

public class ConsoleUtils {
    private static Scanner in = new Scanner(System.in);
    private static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static char selectionMenu() {
        clearScreen();
        String title =    "-------------[imagefilters]-------------";
        String subtitle = "[a] ASCIIFILTER  [p] PIXELSORT  [q] Exit";
        String footer =   "----------------------------------------";

        System.out.print(title + "\n" + subtitle + "\n" + footer + "\nSelection: ");
        return in.next().toLowerCase().charAt(0);
    }

    public static SelectionResult executeSelection(char selection) {
        clearScreen();
        SelectionResult result = null;
        switch (selection) {
            case 'a' -> result =  new SelectionResult("-------------[ASCIIFILTER]-------------", false, new Asciifilter());
            case 'p' -> result =   new SelectionResult("--------------[PIXELSORT]--------------", false, new Pixelsort());
            case 'q' -> result =   new SelectionResult(null, true, null);
        }
        return result;
    }

    public static InputImagePaths getImagePaths(String titleChoice) {
        String subtitle = "Image path: ";
        System.out.print(titleChoice + "\n" + subtitle);

        String imagePath = in.next().replace("\"", "");

        ConsoleUtils.clearScreen();

        subtitle = "Image path: " + imagePath + "\nMask path: ";
        System.out.print(titleChoice + "\n" + subtitle);

        String maskPath = in.next().replace("\"", "");
        if(maskPath.isEmpty()) {
            maskPath = null;
        }
        ConsoleUtils.clearScreen();
        return new InputImagePaths(imagePath, maskPath);
    }

    public static void saveResultImage(BufferedImage resultImage, String imagePath) {
        String outName = imagePath.substring(imagePath.lastIndexOf("\\") + 1, imagePath.lastIndexOf("."));
        try {
            ImageIO.write(resultImage, "png", new File("./filtered_" + outName + ".png"));
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
