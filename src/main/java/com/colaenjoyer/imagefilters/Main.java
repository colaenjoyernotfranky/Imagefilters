package com.colaenjoyer.imagefilters;

import java.awt.image.BufferedImage;

import com.colaenjoyer.imagefilters.filters.ImageFilter;
import com.colaenjoyer.imagefilters.utils.ConsoleUtils;
import com.colaenjoyer.imagefilters.utils.InputImagePaths;
import com.colaenjoyer.imagefilters.utils.SelectionResult;

public class Main {
    public static void main(String[] args) {
        ImageFilter selectedImageFilter = null;
        BufferedImage filterResult;
        boolean quit = false;
        while(true) {
            char selection = ConsoleUtils.selectionMenu();

            SelectionResult selectionResult = ConsoleUtils.executeSelection(selection);
            quit = selectionResult.quit();
            selectedImageFilter = selectionResult.imageFilter();

            if(quit) {
                break;
            }

            InputImagePaths inputImagePaths = ConsoleUtils.getImagePaths(selectionResult.titleChoice());

            if(selectedImageFilter != null) {
                filterResult = selectedImageFilter.execute(inputImagePaths.imagePath(), inputImagePaths.maskPath());
                ConsoleUtils.saveResultImage(filterResult, inputImagePaths.imagePath());
            }
        }
    }
}
