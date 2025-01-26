package co.edu.uptc.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesReader {
    public static String getProperty(String key){
        Properties properties = new Properties();
        String value = "";
        try {
            properties.load(new FileInputStream("resources\\PropertiesGame.properties"));
            value = properties.getProperty(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return value;
    }
}
