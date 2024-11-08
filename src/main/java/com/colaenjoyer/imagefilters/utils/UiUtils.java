package com.colaenjoyer.imagefilters.utils;

import com.colaenjoyer.imagefilters.ui.Menu;
import com.colaenjoyer.imagefilters.ui.MenuBar;
import com.colaenjoyer.imagefilters.ui.MenuItem;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class UiUtils {
    private UiUtils() {}

    public static String generateLineSeparators(int length) {
        return "-".repeat(Math.max(0, length));
    }

    public static List<MenuItem> getSelectionMenuItems() {
        return Arrays.asList(MenuItem.builder()
                .title("asciifilter")
                .selectionChar('a')
                .build(), MenuItem.builder()
                .title("pixelsort")
                .selectionChar('p').build(), MenuItem.builder()
                .title("quit")
                .selectionChar('q').build());
    }

    public static List<MenuBar> getSelectionMenuBars() {
        return Collections.singletonList(MenuBar.builder()
                .title("filterSelection")
                .menuItems(getSelectionMenuItems())
                .build());
    }

    public static Menu getSelectionMenu() {
        return Menu.builder().title("main menu").menuBars(getSelectionMenuBars()).build();
    }
}
