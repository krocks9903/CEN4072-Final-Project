package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Reads configuration values from config.properties.
 */
public class ConfigReader {

    private static Properties properties;

    static {
        try {
            properties = new Properties();
            FileInputStream fis = new FileInputStream("src/test/resources/config.properties");
            properties.load(fis);
            fis.close();
        } catch (IOException e) {
            throw new RuntimeException("Could not load config.properties: " + e.getMessage());
        }
    }

    public static String getEmail() {
        return properties.getProperty("linkedin.email");
    }

    public static String getPassword() {
        return properties.getProperty("linkedin.password");
    }

    public static String getBaseUrl() {
        return properties.getProperty("linkedin.base.url");
    }
}
