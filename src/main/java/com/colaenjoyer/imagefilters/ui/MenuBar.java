package com.colaenjoyer.imagefilters.ui;

import lombok.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.colaenjoyer.imagefilters.utils.UiUtils.generatePaddingForMenuBar;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuBar {
    private String title;
    private List<MenuItem> menuItems;
    private int length;

    public MenuBar(String title) {
        menuItems = new ArrayList<>();
        this.title = title;
    }

    public String generateBar(int length) {
        StringBuilder menuBarString = new StringBuilder("| ");
        boolean hasSelectionChar;
        String padding = "";
        for (MenuItem menuItem : menuItems) {
                hasSelectionChar = menuItem.selectionChar() != 0;
                if(length != this.length) {
                    padding = generatePaddingForMenuBar((length - (menuItem.title().length() + 4)) / 2);
                }
                menuBarString.append(hasSelectionChar ? ("[" + padding + "(") : ("[" + padding))
                        .append(hasSelectionChar ? menuItem.selectionChar() : "")
                        .append(hasSelectionChar ? ") " : "")
                        .append(menuItem.title().toUpperCase())
                        .append(padding);
                String finalPadding = (length != this.length && menuBarString.length() < length) ?
                        " ".repeat(length - (menuBarString.length() + 1)) + "]" : "]";
                menuBarString.append(finalPadding);
        }
        menuBarString.append(" |");
        return menuBarString.toString();
    }

    public int getMenuBarLength() {
        StringBuilder menuBarString = new StringBuilder("| ");
        boolean hasSelectionChar;
        for (MenuItem menuItem : menuItems) {
            hasSelectionChar = menuItem.selectionChar() != 0;
            menuBarString.append(hasSelectionChar ? "[(" : "[")
                    .append(hasSelectionChar ? menuItem.selectionChar() : "")
                    .append(hasSelectionChar ? ") " : "")
                    .append(menuItem.title().toUpperCase()).append("]");
        }
        length = menuBarString.toString().length();
        return length;
    }


    public void addMenuItems(MenuItem... menuItems) {
        this.menuItems.addAll(Arrays.asList(menuItems));
    }

    public void removeMenuItems(MenuItem... menuItems) {
        this.menuItems.removeAll(Arrays.asList(menuItems));
    }

    public List<MenuItem> findMenuItemsByTitle(String title) {
        return menuItems.stream().filter(menuItem -> menuItem.title().equals(title)).toList();
    }
}
