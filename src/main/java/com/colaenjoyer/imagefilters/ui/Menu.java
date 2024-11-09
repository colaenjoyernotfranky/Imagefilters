package com.colaenjoyer.imagefilters.ui;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

import static com.colaenjoyer.imagefilters.utils.UiUtils.generateLineSeparators;

@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Menu {
    private String title;
    private int width;
    private int height;

    private String header;
    private List<MenuBar> menuBars;
    private String footer;

    public List<String> generateMenuBars() {
        checkMenuDimensions();
        generateHeaderAndFooter();
        List<String> rows = generateMenuFrame();
        int menuBarsSize = (Math.min(menuBars.size(), rows.size()));

        if (!menuBars.isEmpty()) {
            for (int y = 0; y < menuBarsSize; y++) {
                char[] currentRow = rows.get(y).toCharArray();
                rows.set(y, insertItemsIntoRow(currentRow, y));
            }
        }
        return rows;
    }

    private String insertItemsIntoRow(char[] currentRow, int menuBarIndex) {
        String menuBarString = menuBars.get(menuBarIndex).generateBar();
        int menuBarLength = menuBarString.length();
        if(menuBarLength > width - 4) {
            int lastIndexOfItemBegin = menuBarString.lastIndexOf("[");
            int lastIndexOfItemEnd = menuBarString.lastIndexOf("]");
            if(lastIndexOfItemEnd >= 0 && lastIndexOfItemBegin >= 0) {
                String lastMenuItem = menuBarString.substring(lastIndexOfItemBegin, lastIndexOfItemEnd + 1);
                menuBarString = menuBarString.replace(lastMenuItem, "");
                menuBarLength = menuBarString.length();
            } else {
                menuBarLength = menuBarLength - 5;
            }
        }
        for (int x = 0; x < menuBarLength; x++) {
            currentRow[x+2] = menuBarString.charAt(x);
        }
        return String.copyValueOf(currentRow);
    }

    private void checkMenuDimensions() {
        if (height < 16) {
            height = 16;
        }
        if (width < 16) {
            width = 16;
        }
    }

    private List<String> generateMenuFrame() {
        List<String> rows = new ArrayList<>();
        for(int y = 0; y < height/5; y++) {
            rows.add("| " + " ".repeat(width - 4) + " |");
        }
        return rows;
    }

    private void generateHeaderAndFooter() {
        header = "+" + generateLineSeparators(((width - title.length() - 4) / 2))
               + "[" + title + "]" +
                generateLineSeparators(((width - title.length() - 4) / 2));
        if(((header.length() + 1) % 2) != 0) {header += "-+";} else { header += "+";}
        footer = "+" + generateLineSeparators(width - 2) + "+";
    }
}
