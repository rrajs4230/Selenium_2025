package com.example.selenium.utils;


import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Properties;

public class LocatorReader {
	private static String projectRootPath = Paths.get(System.getProperty("user.dir"), "src", "test", "resources").toString();
    private static Properties properties;

    // Static block to load properties once
    static {
        properties = new Properties();
        try {
            FileInputStream file = new FileInputStream(Paths.get(projectRootPath, "locators.properties").toString());
            properties.load(file);
        } catch (IOException e) {
            throw new RuntimeException(" Error loading locators file!", e);
        }
    }

    public static String getLocator(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            throw new RuntimeException(" Locator not found for key: " + key);
        }
        return value;
    }
}
