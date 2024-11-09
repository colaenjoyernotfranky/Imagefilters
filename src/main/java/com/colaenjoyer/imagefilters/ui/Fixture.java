package com.colaenjoyer.imagefilters.ui;


import com.colaenjoyer.imagefilters.utils.ConsoleUtils;

import java.util.Arrays;
import java.util.List;

import static com.colaenjoyer.imagefilters.utils.CommandUtils.getCommandExecution;
import static com.colaenjoyer.imagefilters.utils.UiUtils.*;

public class Fixture {
    private Fixture() {}

    public static List<MenuItem> getSelectionMenuItems() {
        return Arrays.asList(MenuItem.builder()
                .title("asciifilter")
                .selectionChar('a')
                .function(getCommandExecution(ConsoleUtils::executeSelection, 'a'))
                .build(), MenuItem.builder()
                .title("pixelsort")
                .selectionChar('p')
                .function(getCommandExecution(ConsoleUtils::executeSelection, 'p'))
                .build(), MenuItem.builder()
                .title("quit")
                .selectionChar('q')
                .function(getCommandExecution(ConsoleUtils::executeSelection, 'q'))
                .build());
    }

    public static List<MenuBar> getSelectionMenuBars() {
        return Arrays.asList(
                getParagraphMenuBar("selectionParagraph", "Select a filter:"),
                getParagraphMenuBar("asciiFilterInfo", "Asciifilter - Converts Image into Ascii art."),
                getParagraphMenuBar("pixelSortInfo", "Pixelsort - Sorts image pixels by color."),
                getEmptyMenuBar(),
                getSelectionMenuBar("filterSelection", getSelectionMenuItems())
        );
    }

    public static Menu getSelectionMenu() {
        return Menu.builder().title("imagefilters").width(48).height(25).menuBars(getSelectionMenuBars()).build();
    }
}
