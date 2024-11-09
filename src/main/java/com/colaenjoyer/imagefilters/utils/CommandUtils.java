package com.colaenjoyer.imagefilters.utils;

import com.colaenjoyer.imagefilters.ui.Function;

import java.util.function.Consumer;

public class CommandUtils {
    private CommandUtils() {}

    public static Function<Character> getCommandExecution(Consumer<Character> method, char parameter) {
        return new Function<>(method, parameter);
    }

    public static Function<String> getCommandExecution(Consumer<String> method, String parameter) {
        return new Function<>(method, parameter);
    }

    public static Function<Boolean> getCommandExecution(Consumer<Boolean> method, boolean parameter) {
        return new Function<>(method, parameter);
    }

    public static Function<Integer> getCommandExecution(Consumer<Integer> method, int parameter) {
        return new Function<>(method, parameter);
    }
}
