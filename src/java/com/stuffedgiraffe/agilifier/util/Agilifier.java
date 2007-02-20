package com.stuffedgiraffe.agilifier.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Agilifier {

    public static String uncamel(String name) {
        StringBuffer b = new StringBuffer();
        char[] chars = name.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (Character.isUpperCase(c) && (nextCharacterIsNotUpperCase(chars, i) || previousCharacterIsNotUpperCase(chars, i)))
            {
                b.append(" ");
            }
            if (Character.isDigit(c) && previousCharacterIsNotDigit(chars, i)) {
                b.append(" ");
            }
            b.append(c);
        }
        String result = b.toString();
        return result.trim();
    }

    private static boolean previousCharacterIsNotDigit(char[] chars, int i) {
        i--;
        if (i >= chars.length) {
            return true;
        }
        return !Character.isDigit(chars[i]);
    }

    private static boolean nextCharacterIsNotUpperCase(char[] chars, int i) {
        i++;
        if (i >= chars.length) {
            return true;
        }
        return !Character.isUpperCase(chars[i]);
    }

    private static boolean previousCharacterIsNotUpperCase(char[] chars, int i) {
        i--;
        if (i < 0) {
            return true;
        }
        return !Character.isUpperCase(chars[i]);
    }


    private static boolean treatExistingResultFileAsSuccess = false;

    public static boolean treatExistingResultFileAsSuccess() {
        return treatExistingResultFileAsSuccess;
    }

    public static void treatExistingResultFileAsSuccess(boolean treatExistingResultFileAsSuccess) {
        Agilifier.treatExistingResultFileAsSuccess = treatExistingResultFileAsSuccess;
    }

    public static Properties readPropertiesFromFile() throws IOException {
        InputStream propertiesStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("agilifier.properties");
        if (propertiesStream == null) {
            throw new IllegalStateException("Could not find file in classpath: agilifier.properties");
        }
        Properties properties = new Properties();
        properties.load(propertiesStream);
        return properties;
    }

    public static String getProperty(String key, String defaultValue) throws IOException {
        String value = System.getProperty(key);
        if (value == null) {
            value = readPropertiesFromFile().getProperty(key, defaultValue);
        }
        return value;
    }

    public static String getModuleName() throws IOException {
        return getProperty("agilifier.projectName", "Project");
    }
}
