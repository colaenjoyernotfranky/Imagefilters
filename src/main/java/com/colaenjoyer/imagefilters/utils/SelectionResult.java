package com.colaenjoyer.imagefilters.utils;

import com.colaenjoyer.imagefilters.filters.ImageFilter;

import lombok.Builder;

@Builder
public record SelectionResult(boolean quit, ImageFilter imageFilter) {}
