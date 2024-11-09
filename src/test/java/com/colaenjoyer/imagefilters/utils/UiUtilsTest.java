package com.colaenjoyer.imagefilters.utils;

import com.colaenjoyer.imagefilters.ui.MenuBar;
import com.colaenjoyer.imagefilters.ui.MenuItem;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static com.colaenjoyer.imagefilters.utils.UiUtils.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;


public class UiUtilsTest {
    @Test
    public void shouldGetSelectionMenuBar() {
        List<MenuItem> menuItems = Collections.singletonList(MenuItem.builder().build());
        MenuBar shouldReturn = MenuBar.builder().title("bla").menuItems(menuItems).build();

        MenuBar result = getSelectionMenuBar("bla", menuItems);

        assertEquals(shouldReturn.getTitle(), result.getTitle());
        assertEquals(shouldReturn.getMenuItems(), result.getMenuItems());
    }

    @Test
    public void shouldGetParagraphMenuBar() {
        MenuBar shouldReturn = MenuBar.builder().title("bla")
                .menuItems(Collections.singletonList(MenuItem.builder().title("bla bla bla").build()))
                .build();

        MenuBar result = getParagraphMenuBar("bla", "bla bla bla");

        assertEquals(shouldReturn.getTitle(), result.getTitle());
        assertEquals(shouldReturn.getMenuItems().getFirst().title(), result.getMenuItems().getFirst().title());
        assertEquals(shouldReturn.getMenuItems().getFirst().selectionChar(),
                result.getMenuItems().getFirst().selectionChar());
        assertNull(result.getMenuItems().getFirst().function());
    }

    @Test
    public void shouldGetEmptyMenuBar() {
        MenuBar shouldReturn = MenuBar.builder().title("emptyBar")
                .menuItems(Collections.singletonList(MenuItem.builder().title("").build()))
                .build();

        MenuBar result = getEmptyMenuBar();

        assertEquals(shouldReturn.getTitle(), result.getTitle());
        assertEquals(shouldReturn.getMenuItems().getFirst().title(), result.getMenuItems().getFirst().title());
        assertEquals(shouldReturn.getMenuItems().getFirst().selectionChar(),
                result.getMenuItems().getFirst().selectionChar());
        assertNull(result.getMenuItems().getFirst().function());
    }

    @Test
    public void shouldGenerateLineSeparators() {
        String shouldReturn = "-----";

        String result = generateLineSeparators(5);

        assertEquals(shouldReturn.length(), result.length());
        assertEquals('-', result.charAt(0));
    }
}
