package com.selcuk.ProjectUtils;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class PropertyUtils {
    public static Properties loadPropertiesFile() {

        Properties prop = new Properties();
        try {
            FileReader fr = new FileReader(
                    System.getProperty("user.dir") + "\\src\\test\\resources\\projectdata.properties");
            prop.load(fr);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return prop;

    }
    public static Properties storePropertiesFile(Properties prop) {
        try {
            FileWriter fw = new FileWriter(
                    System.getProperty("user.dir") + "\\src\\test\\resources\\projectdata.properties");
            prop.store(fw,"Updated Properties file");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return prop;
    }
}
