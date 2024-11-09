package com.colaenjoyer.imagefilters.ui;

import lombok.*;

import java.util.Arrays;
import java.util.List;

import static com.colaenjoyer.imagefilters.utils.UiUtils.generateLineSeparators;

@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Menu {
    private String title;
    private String header;
    private List<MenuBar> menuBars;
    private String footer;

    public String[] generateMenuBars() {
        int longestMenuBarLength = generateHeaderAndFooter();
        String[] menuBarsArray = new String[menuBars.size()];
        for (int i = 0; i < menuBars.size(); i++) {
            menuBarsArray[i] = menuBars.get(i).generateBar(longestMenuBarLength);
        }
        return menuBarsArray;
    }

    private int generateHeaderAndFooter() {
        int totalLength = getLongestMenuBarLength();
        header = "+" + generateLineSeparators(((totalLength - title.length() - 2) / 2))
               + "[" + title + "]" +
                generateLineSeparators(((totalLength - title.length() - 2) / 2)) + "+";
        footer = "+" + generateLineSeparators(totalLength) + "+";
        return totalLength;
    }

    private int getLongestMenuBarLength() {
        int longestMenuBarLength = 0;
        for (MenuBar menuBar : menuBars) {
            int menuBarLength = menuBar.getMenuBarLength();
            if(menuBarLength > longestMenuBarLength) {
                longestMenuBarLength = menuBarLength;
            }
        }
        return longestMenuBarLength;
    }

    public void addMenuBars(MenuBar... menuBars) {
        this.menuBars.addAll(Arrays.asList(menuBars));
    }

    public void removeMenuBars(MenuBar... menuBars) {
        this.menuBars.removeAll(Arrays.asList(menuBars));
    }

    public List<MenuBar> findMenuBarsByTitle(String title) {
        return menuBars.stream().filter(menuBar -> menuBar.getTitle().equals(title)).toList();
    }
}
