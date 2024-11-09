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

    public static String generatePaddingForMenuBar(int length) {
        return " ".repeat(Math.max(0, length));
    }

    public static List<MenuItem> getSelectionMenuItems() {
        return Arrays.asList(MenuItem.builder()
                .title("asciifilter")
                .selectionChar('a')
                .build(), MenuItem.builder()
                .title("pixelsort")
                .selectionChar('p').build(), MenuItem.builder()
                .title("quit")
                .selectionChar('q')
                .build());
    }

    public static MenuBar getSelectionMenuBar(String title, MenuItem... items) {
        return MenuBar.builder()
                .title(title)
                .menuItems(Arrays.asList(items))
                .build();
    }

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

    public static List<MenuBar> getSelectionMenuBars() {
        return Arrays.asList(
                getParagraphMenuBar("selectionParagraph", "Select a filter"),
                getEmptyMenuBar(),
                getSelectionMenuBar("filterSelection", getSelectionMenuItems())
        );
    }

    public static Menu getSelectionMenu() {
        return Menu.builder().title("imagefilters").menuBars(getSelectionMenuBars()).build();
    }
}
