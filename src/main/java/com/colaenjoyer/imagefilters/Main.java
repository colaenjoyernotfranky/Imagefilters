package com.colaenjoyer.imagefilters;

import java.awt.image.BufferedImage;

import com.colaenjoyer.imagefilters.filters.ImageFilter;
import com.colaenjoyer.imagefilters.utils.ConsoleUtils;
import com.colaenjoyer.imagefilters.utils.InputImagePaths;
import com.colaenjoyer.imagefilters.utils.SelectionResult;
import lombok.extern.java.Log;

@Log
public class Main {
    public static void main(String[] args) {
        ImageFilter selectedImageFilter;
        BufferedImage filterResult;
        boolean quit;
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
                if(filterResult != null) {
                    ConsoleUtils.saveResultImage(filterResult, inputImagePaths.imagePath(), selectedImageFilter);
                } else {
                    log.severe("filterResult is null");
                }
            }
        }
    }
}
