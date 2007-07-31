package com.stuffedgiraffe.agilifier.main;

import com.stuffedgiraffe.agilifier.util.Agilifier;
import com.stuffedgiraffe.agilifier.util.DeafTestListener;
import junit.framework.TestSuite;

import java.io.IOException;

public class AllAcceptanceTests {
    public static void main(String[] args) throws Throwable {
        FileContext fileContext = FileContext.createFromPropertiesFile();
        GenerateUserStorySite.generateModuleSite(Agilifier.getModuleName(), fileContext, new DeafTestListener());
    }

    public static TestSuite suite() throws Throwable {
        try {
            if ("true".equals(Agilifier.getProperty("agilifier.treatExistingResultFileAsSuccess", "false"))) {
                Agilifier.treatExistingResultFileAsSuccess(true);
            }
            String moduleName = Agilifier.getModuleName();
            FileContext fileContext = FileContext.createFromPropertiesFile();
            TestSuite testSuite = GenerateUserStorySite.suite(moduleName, fileContext, new DeafTestListener());
            return testSuite;
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }

}
