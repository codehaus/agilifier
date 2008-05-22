package com.stuffedgiraffe.agilifier;

import com.stuffedgiraffe.agilifier.builder.UserStoriesSuiteBuilder;
import com.stuffedgiraffe.agilifier.main.FileContext;
import com.stuffedgiraffe.agilifier.model.AcceptanceTest;
import com.stuffedgiraffe.agilifier.model.Module;
import com.stuffedgiraffe.agilifier.model.UserStory;
import com.stuffedgiraffe.agilifier.model.UserStorySuite;
import com.stuffedgiraffe.agilifier.util.Agilifier;
import junit.framework.TestCase;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class AcceptanceTestFileStructureTest extends TestCase {
    private List failures = new LinkedList();

    public void testTestNameAppearsInTestFileCorrectly() throws Exception {
        FileContext fileContext = FileContext.createFromPropertiesFile();
        UserStoriesSuiteBuilder factory = new UserStoriesSuiteBuilder();
        Module module = new Module("MyModule", fileContext);
        UserStorySuite userStorySuite = factory.buildAllUserStoriesSuite(fileContext, module);
        for (Iterator i1 = userStorySuite.getStories().iterator(); i1.hasNext();) {
            UserStory userStory = (UserStory) i1.next();
            for (Iterator i2 = userStory.getAllTests().iterator(); i2.hasNext();) {
                AcceptanceTest test = (AcceptanceTest) i2.next();
                File testFile = test.getTestFile();
                checkHeadingInFileIsCorrect(testFile);
            }
        }
        if (!failures.isEmpty()) {
            failThisTest();
        }
    }

    private void failThisTest() {
        StringBuffer stringBuffer = new StringBuffer("Could not find name in test files (" + failures.size() + "): \n");
        for (int i = 0; i < failures.size(); i++) {
            File file = (File) failures.get(i);
            stringBuffer.append("    - ").append(file).append("\n");
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
        testName = Agilifier.uncamel(testName);
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
