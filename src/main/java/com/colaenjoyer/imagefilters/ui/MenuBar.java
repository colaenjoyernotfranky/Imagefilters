package com.colaenjoyer.imagefilters.ui;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuBar {
    private String title;
    private List<MenuItem> menuItems;

    public MenuBar(String title) {
        menuItems = new ArrayList<>();
        this.title = title;
    }

    public String generateBar() {
        StringBuilder menuBarString = new StringBuilder("| ");
        for (MenuItem menuItem : menuItems) {
            menuBarString.append("[(").append(menuItem.selectionChar()).append(") ")
                    .append(menuItem.title().toUpperCase()).append("]");
        }
        menuBarString.append(" |");
        return menuBarString.toString();
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
