package com.colaenjoyer.imagefilters;

import java.util.Collections;
import com.colaenjoyer.imagefilters.ui.MenuRenderer;
import com.colaenjoyer.imagefilters.utils.ConsoleUtils;
import com.colaenjoyer.imagefilters.utils.ImageFiltersOperations;
import lombok.extern.java.Log;

import static com.colaenjoyer.imagefilters.ui.Fixture.getSelectionMenu;

@Log
public class ImageFilters {
    public static void main(String[] args) {
        while(true) {
            ConsoleUtils.clearScreen();

            MenuRenderer menuRenderer = MenuRenderer.builder()
                    .menus(Collections.singletonList(getSelectionMenu())).build();
            menuRenderer.selectMenu("imagefilters");

            char selection = ImageFiltersOperations.selectionMenu();

            if(selection == 'q') {
                break;
            }

            menuRenderer.execute(selection);
        }
    }
}
