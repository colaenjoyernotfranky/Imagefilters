package com.colaenjoyer.imagefilters.utils;

import com.colaenjoyer.imagefilters.ui.MenuBar;
import com.colaenjoyer.imagefilters.ui.MenuItem;

import java.util.Collections;
import java.util.List;

public class UiUtils {
    private UiUtils() {}

    public static MenuBar getSelectionMenuBar(String title, List<MenuItem> items) {
        return MenuBar.builder()
                .title(title)
                .menuItems(items)
                .build();
    }

    public static MenuBar getParagraphMenuBar(String title, String paragraph) {
        return MenuBar.builder()
                .title(title)
                .menuItems(Collections.singletonList(MenuItem.builder().title(paragraph).build()))
                .build();
    }

    public static MenuBar getEmptyMenuBar() {
        return MenuBar.builder()
                .title("emptyBar")
                .menuItems(Collections.singletonList(MenuItem.builder().title("").build()))
                .build();
    }

    public static String generateLineSeparators(int length) {
        return "-".repeat(Math.max(0, length));
    }
}
