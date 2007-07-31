package com.stuffedgiraffe.agilifier;

import com.stuffedgiraffe.agilifier.util.CamelUtils;
import junit.framework.TestCase;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AcceptanceTestFileStructureTest extends TestCase {
    private List<File> failures = new ArrayList<File>();

    // TODO - Fix this
//    public void testTestNameAppearsInTestFileCorrectly() throws Exception {
//        FileContext fileContext = FileContext.createFromPropertiesFile();
//        UserStoriesSuiteBuilder factory = new UserStoriesSuiteBuilder();
//        Module module = new Module("MyModule", fileContext);
//        UserStorySuite userStorySuite = factory.buildAllUserStoriesSuite(fileContext, module);
//        for (AcceptanceTestOrAcceptanceTestContainer userStory : userStorySuite.getChildren()) {
//            for (AcceptanceTest acceptanceTest : userStory.getAll()) {
//                File testFile = acceptanceTest.getTestFile();
//                checkHeadingInFileIsCorrect(testFile);
//            }
//        }
//        if (!failures.isEmpty()) {
//            failThisTest();
//        }
//    }

    private void failThisTest() {
        StringBuffer stringBuffer = new StringBuffer("Could not find name in test files (" + failures.size() + "): \n");
        for (File failure : failures) {
            stringBuffer.append("    - ").append(failure).append("\n");
        }
        fail(stringBuffer.toString());
    }

    private void checkHeadingInFileIsCorrect(File file) throws IOException {
        String filename = file.getName();
        if (filename.indexOf(".html") < 0) {
            System.out.println("File does not have .html extension: " + file);
            return;
        }
        if (filename.startsWith("~$")) {
            System.out.println("File is a stupid Word autosave");
            return;
        }
        String testName = filename.substring(0, filename.indexOf(".html"));
        testName = CamelUtils.uncamel(testName);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = reader.readLine();
        boolean found = false;
        while (line != null) {
            if (line.indexOf(testName) >= 0) {
                found = true;
            }
            line = reader.readLine();
        }
        reader.close();
        if (!found) {
            failures.add(file);
        }
    }

}
