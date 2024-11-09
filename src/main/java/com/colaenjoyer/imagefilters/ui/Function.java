package com.colaenjoyer.imagefilters.ui;

import lombok.Builder;

import java.util.function.Consumer;

@Builder
public class Function<T> {
    private Consumer<T> method;
    private T parameter;

    public Function(Consumer<T> method, T parameter) {
        this.method = method;
        this.parameter = parameter;
    }

    public void execute() {
        method.accept(parameter);
    }
}
