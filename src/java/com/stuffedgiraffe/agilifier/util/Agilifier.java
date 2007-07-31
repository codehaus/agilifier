package com.stuffedgiraffe.agilifier.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Properties;

public class Agilifier {
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

    public static String getStackTrace(Throwable exception) {
        StringWriter stringWriter = new StringWriter();
        exception.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }

}
