package com.colaenjoyer.imagefilters;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Scanner;

import javax.imageio.ImageIO;

import com.colaenjoyer.imagefilters.filters.Asciifilter;
import com.colaenjoyer.imagefilters.filters.ImageFilter;
import com.colaenjoyer.imagefilters.filters.Pixelsort;

public class Main {
    public static void main(String[] args) {
        ImageFilter selectedImageFilter = null;
        BufferedImage filterResult;
        Scanner in = new Scanner(System.in);

        clearScreen();  

        String title =    "--------[imagefilters]--------";
        String subtitle = "[a] ASCIIFILTER  [p] PIXELSORT";

        System.out.print(title + "\n" + subtitle + "\n");
        
        char selection = in.next().toLowerCase().charAt(0);

        clearScreen();  

        switch (selection) {
            case 'a' -> title =    "--------[ASCIIFILTER]---------";
            case 'p' -> title =    "---------[PIXELSORT]----------";
        }
        
        subtitle = "Image path: ";
        System.out.print(title + "\n" + subtitle);

        String imagePath = in.next().replace("\"", "");


        clearScreen();

        subtitle = "Image path: " + imagePath + "\nMask path: ";
        System.out.print(title + "\n" + subtitle);

        String maskPath = in.next().replace("\"", "");
        if(maskPath.isEmpty()) {
            maskPath = null;
        }

        clearScreen();

        subtitle = "Image path:" + imagePath + "\nMask path: " + maskPath;
        System.out.print(title + "\n" + subtitle);
        
        switch (selection) {
            case 'a' -> selectedImageFilter = new Asciifilter();
            case 'p' -> selectedImageFilter = new Pixelsort();
        }

        if(selectedImageFilter != null) {
            filterResult = selectedImageFilter.execute(imagePath, maskPath);

            String outName = imagePath.substring(imagePath.lastIndexOf("\\") + 1, imagePath.lastIndexOf("."));
            try {
                ImageIO.write(filterResult, "png", new File("./filtered_" + outName + ".png"));
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
    
    private static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
