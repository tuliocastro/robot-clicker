package br.com.tlabs.experiments;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigurationReader {

    private final String FILE = "configuration.properties";

    private static ConfigurationReader instance;

    private Properties properties;

    private ConfigurationReader() {

        this.properties = new Properties();

        readProperties();
    }

    private void readProperties() {

        try {

            FileInputStream fis = new FileInputStream(FILE);

            properties.load(fis);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void update() {
        readProperties();
    }

    public static ConfigurationReader getInstance() {

        if (instance == null) {
            instance = new ConfigurationReader();
        }

        return instance;
    }

    public Object get(String key) {

        return get(key, "");

    }

    public String get(String key, Object defaultValue) {

        if (properties == null) {
            throw new RuntimeException("Properties not loaded!");
        }

        String convertedDefaultValue = String.valueOf(defaultValue);

        return properties.getProperty(key, convertedDefaultValue);
    }

}
