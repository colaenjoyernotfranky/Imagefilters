package com.colaenjoyer.imagefilters.ui;

import lombok.Builder;

@Builder
public record MenuItem(char selectionChar, String title, Function<?> function) {}
