package com.colaenjoyer.imagefilters.configuration;

import com.colaenjoyer.imagefilters.utils.ConfigUtils;

public final class AppConfiguration {
    private static final String OUTPUT_PATH = ConfigUtils.getEnvironmentVariable("OUTPUT_PATH", System.getProperty("user.dir"));

    private AppConfiguration() {}

    public static String getOutputPath() {
        return OUTPUT_PATH;
    }
}
