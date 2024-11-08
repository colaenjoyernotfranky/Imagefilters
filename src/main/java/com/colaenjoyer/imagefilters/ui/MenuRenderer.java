package com.colaenjoyer.imagefilters.ui;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.java.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Builder
@Getter
@Log
@AllArgsConstructor
public class MenuRenderer {
    private List<Menu> menus;
    private Menu selectedMenu;

    public MenuRenderer() {
        menus = new ArrayList<>();
    }

    public MenuRenderer(List<Menu> menus) {
        this.menus = menus;
    }

    public void selectMenu(String title) {
        List<Menu> menusWithTitle = findMenusByTitle(title);
        if (!menusWithTitle.isEmpty()) {
            selectedMenu = menusWithTitle.get(0);
        } else {
            log.warning("No such menu: " + title);
        }
        draw();
    }

    private void draw() {
        String[] menuBars = selectedMenu.generateMenuBars();
        System.out.println(selectedMenu.getHeader());
        for (String menuBar : menuBars) {
            System.out.println(menuBar);
        }
        System.out.print(selectedMenu.getFooter());
    }

    public void addMenus(Menu... menus) {
        this.menus.addAll(Arrays.asList(menus));
    }

    public void removeMenus(Menu... menus) {
            this.menus.removeAll(Arrays.asList(menus));
    }

    public List<Menu> findMenusByTitle(String title) {
        return menus.stream().filter(menuBar -> menuBar.getTitle().equals(title)).toList();
    }
}
