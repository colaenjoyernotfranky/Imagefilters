package com.colaenjoyer.imagefilters.utils;

import lombok.Builder;

@Builder
public record InputImagePaths(String imagePath, String maskPath) {}
