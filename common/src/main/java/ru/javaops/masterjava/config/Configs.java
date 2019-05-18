package ru.javaops.masterjava.config;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.io.File;

/**
 * gkislin
 * 01.11.2016
 */
public class Configs {

    public static Config getConfig(String resource) {
        return ConfigFactory.parseResources(resource).resolve();
    }

    public static Config getConfig(String resource, String domain) {
        return getConfig(resource).getConfig(domain);
    }

    public static File getConfigFile(String path) {
        return new File(AppConfig.APPCONF.getString("configDir"), path);
    }

    private static class AppConfig {
        private static final Config APPCONF = getConfig("app.conf", "app");
    }
}
