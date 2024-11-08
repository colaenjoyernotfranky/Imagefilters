package com.colaenjoyer.imagefilters.ui;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

import static com.colaenjoyer.imagefilters.utils.UiUtils.generateLineSeparators;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Menu {
    private String title;
    private String header;
    private List<MenuBar> menuBars;
    private String footer;

    public String[] generateMenuBars() {
        generateHeaderAndFooter();
        String[] menuBarsArray = new String[menuBars.size()];
        for (int i = 0; i < menuBars.size(); i++) {
            menuBarsArray[i] = menuBars.get(i).generateBar();
        }
        return menuBarsArray;
    }

    private void generateHeaderAndFooter() {
        int totalLength = getLongestMenuBarLength();
        header = "+" + generateLineSeparators(((totalLength - title.length() - 4) / 2))
               + "[" + title + "]" +
                generateLineSeparators(((totalLength - title.length() - 4) / 2)) + "+";
        footer = "+" + generateLineSeparators(totalLength - 2) + "+";
    }

    private int getLongestMenuBarLength() {
        int longestMenuBarLength = 0;
        for (MenuBar menuBar : menuBars) {
            int menuBarLength = menuBar.generateBar().length();
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
