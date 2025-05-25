package com.selcuk.TutorialsNinja.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File; // For File.separator
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

/**
 * PropertyUtils class provides utility methods for loading and managing properties
 * from a standard Java .properties file. It simplifies accessing configuration data
 * stored in `projectdata.properties` located in `src/test/resources`.
 */
public class PropertyUtils {
    private static final Logger log = LogManager.getLogger(PropertyUtils.class);
    private static final String PROPERTIES_FILE_PATH = System.getProperty("user.dir") +
                                                       File.separator + "src" +
                                                       File.separator + "test" +
                                                       File.separator + "resources" +
                                                       File.separator + "projectdata.properties";

    /**
     * Loads properties from the `projectdata.properties` file.
     * The properties file path is predefined as a constant within this class.
     *
     * @return A {@link Properties} object containing the loaded properties.
     *         Returns an empty Properties object if the file cannot be loaded or an error occurs.
     */
    public static Properties loadPropertiesFile() {
        log.info("Executing method: loadPropertiesFile");
        log.debug("Attempting to load properties from: {}", PROPERTIES_FILE_PATH);
        Properties prop = new Properties();
        try (FileReader fr = new FileReader(PROPERTIES_FILE_PATH)) {
            prop.load(fr);
            log.info("Properties loaded successfully from: {}", PROPERTIES_FILE_PATH);
            if (log.isDebugEnabled()) { // Log all properties at debug level
                prop.forEach((key, value) -> log.debug("Loaded property: {} = {}", key, value));
            }
        } catch (IOException e) {
            log.error("IOException while loading properties file {}: {}", PROPERTIES_FILE_PATH, e.getMessage(), e);
            // Depending on requirements, could return null or an empty Properties object,
            // or rethrow as a custom runtime exception
        }
        log.info("Method loadPropertiesFile completed.");
        return prop;
    }

    /**
     * Stores the given {@link Properties} object to the `projectdata.properties` file.
     *
     * @param prop The Properties object to store.
     * @param comments A description of the property list, to be included as a comment in the file.
     * @return The same Properties object that was passed in, for convenience.
     */
    public static Properties storePropertiesFile(Properties prop, String comments) {
        log.info("Executing method: storePropertiesFile");
        log.debug("Attempting to store properties to: {} with comments: '{}'", PROPERTIES_FILE_PATH, comments);
        try (FileWriter fw = new FileWriter(PROPERTIES_FILE_PATH)) {
            prop.store(fw, comments);
            log.info("Properties stored successfully to: {}", PROPERTIES_FILE_PATH);
            if (log.isDebugEnabled()) {
                prop.forEach((key, value) -> log.debug("Stored property: {} = {}", key, value));
            }
        } catch (IOException e) {
            log.error("IOException while storing properties file {}: {}", PROPERTIES_FILE_PATH, e.getMessage(), e);
        }
        log.info("Method storePropertiesFile completed.");
        return prop;
    }

    /**
     * Stores the given {@link Properties} object to the `projectdata.properties` file
     * with a default comment "Application Properties".
     *
     * @param prop The Properties object to store.
     * @return The same Properties object that was passed in.
     */
    public static Properties storePropertiesFile(Properties prop) {
        return storePropertiesFile(prop, "Application Properties");
    }

    /**
     * Retrieves a property value for the given key from the `projectdata.properties` file.
     *
     * @param key The key of the property to retrieve.
     * @return The value of the property as a String, or {@code null} if the key is not found
     *         or an error occurs during loading.
     */
    public static String getProperty(String key) {
        log.info("Executing method: getProperty with key: {}", key);
        Properties prop = loadPropertiesFile();
        String value = prop.getProperty(key);
        log.info("Method getProperty for key '{}' completed. Returning value: '{}'", key, value);
        return value;
    }

    /**
     * Retrieves a property value for the given key from the `projectdata.properties` file.
     * Returns a default value if the key is not found.
     *
     * @param key The key of the property to retrieve.
     * @param defaultValue The default value to return if the key is not found.
     * @return The value of the property as a String, or the `defaultValue` if the key is not found
     *         or an error occurs during loading.
     */
    public static String getProperty(String key, String defaultValue) {
        log.info("Executing method: getProperty with key: {}, defaultValue: {}", key, defaultValue);
        Properties prop = loadPropertiesFile();
        String value = prop.getProperty(key, defaultValue);
        log.info("Method getProperty for key '{}' completed. Returning value: '{}' (defaultValue was '{}')", key, value, defaultValue);
        return value;
    }

    /**
     * Sets a property (key-value pair) in the `projectdata.properties` file.
     * This method first loads existing properties, sets the new property, and then stores
     * all properties back to the file with a comment indicating which property was updated.
     *
     * @param key The key of the property to set.
     * @param value The value of the property to set.
     */
    public static void setProperty(String key, String value) {
        log.info("Executing method: setProperty with key: {}, value: {}", key, value);
        Properties prop = loadPropertiesFile();
        prop.setProperty(key, value);
        storePropertiesFile(prop, "Updated property: " + key);
        log.info("Method setProperty for key '{}' with value '{}' completed and properties stored.", key, value);
    }
}
