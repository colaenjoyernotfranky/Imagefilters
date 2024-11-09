package com.colaenjoyer.imagefilters.ui;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuBar {
    private String title;
    private List<MenuItem> menuItems;

    public String generateBar() {
        StringBuilder result = new StringBuilder();
        for (MenuItem menuItem : menuItems) {
            result.append(getMenuItem(menuItem));
        }
        return result.toString();
    }

    public String getMenuItem(MenuItem menuItem) {
        String selectionChar = menuItem.selectionChar() != 0 ? "(" + menuItem.selectionChar() + ")" : "";
        String menuItemTitle = menuItem.selectionChar() != 0 ? menuItem.title().toUpperCase() : menuItem.title();
        return menuItem.selectionChar() != 0 ? "[" + selectionChar + menuItemTitle + "]" : menuItemTitle;
    }
}
