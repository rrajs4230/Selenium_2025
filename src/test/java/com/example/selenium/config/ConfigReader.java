package com.example.selenium.config;


import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Properties;

public class ConfigReader {

    private static ConfigReader configReader;
    private static Properties properties=new Properties(); 
    private String projectRootPath = Paths.get(System.getProperty("user.dir"), "src", "test", "resources").toString();
    
    
    

    private ConfigReader() {
        try {
            // Step 1: Load Environment.properties
            try (FileInputStream envFis = new FileInputStream(
                    Paths.get(projectRootPath, "Environment.properties").toString())) {
            	
                properties.load(envFis);
            }

            // Step 2: Retrieve the environment key
            String environment = properties.getProperty("Environment");
            if (environment == null || environment.isEmpty()) {
                throw new RuntimeException("Environment key is missing in Environment.properties!");
            }

            // Step 3: Load the environment-specific properties file
            try (FileInputStream envSpecificFis = new FileInputStream(
                    Paths.get(projectRootPath, environment + ".properties").toString())) {
            	
                properties.load(envSpecificFis);
            }

        } catch (IOException e) {
            throw new RuntimeException("Error loading configuration files!", e);
        }
    }

    public static ConfigReader getInstance() {
        if (configReader == null) {
            synchronized (ConfigReader.class) {
                if (configReader == null) {
                    configReader = new ConfigReader();
                }
            }
        }
        return configReader;
    }

    public String getStringProperty(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            throw new RuntimeException("Key " + key + " not found in properties!");
        }
        return value;
    }

    public int getIntProperty(String key) {
        String value = getStringProperty(key);
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Key " + key + " is not a valid integer in properties!", e);
        }
    }
}
