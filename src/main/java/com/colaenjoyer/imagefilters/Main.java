package com.colaenjoyer.imagefilters;

import java.awt.image.BufferedImage;
import java.util.Collections;

import com.colaenjoyer.imagefilters.filters.ImageFilter;
import com.colaenjoyer.imagefilters.ui.MenuRenderer;
import com.colaenjoyer.imagefilters.utils.ConsoleUtils;
import com.colaenjoyer.imagefilters.utils.InputImagePaths;
import com.colaenjoyer.imagefilters.utils.SelectionResult;
import lombok.extern.java.Log;

import static com.colaenjoyer.imagefilters.utils.UiUtils.*;

@Log
public class Main {
    public static void main(String[] args) {
        ImageFilter selectedImageFilter;
        BufferedImage filterResult;
        boolean quit;
        while(true) {
            ConsoleUtils.clearScreen();

            MenuRenderer menuRenderer = MenuRenderer.builder().menus(Collections.singletonList(getSelectionMenu())).build();
            menuRenderer.selectMenu("main menu");

            char selection = ConsoleUtils.selectionMenu();

            SelectionResult selectionResult = ConsoleUtils.executeSelection(selection);
            quit = selectionResult.quit();
            selectedImageFilter = selectionResult.imageFilter();

            if(quit) {
                break;
            }

            InputImagePaths inputImagePaths = ConsoleUtils.getImagePaths();

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
