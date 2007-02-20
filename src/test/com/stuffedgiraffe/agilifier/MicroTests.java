package com.stuffedgiraffe.agilifier;

import junit.framework.TestSuite;
import org.apache.tools.ant.DirectoryScanner;

import java.io.File;

public class MicroTests extends TestSuite {
    public static TestSuite suite() throws Throwable {
        TestSuite suite = new TestSuite();
        File projectRoot = new File("./src/test/");
        DirectoryScanner scanner = new DirectoryScanner();
        scanner.setBasedir(projectRoot);
        String[] includedTests = new String[]{"**/*Test.java"};
        String[] excludedTests = new String[]{"**/JUnitAcceptanceTest.java"};
        scanner.setIncludes(includedTests);
        scanner.setExcludes(excludedTests);
        scanner.scan();
        String[] files = scanner.getIncludedFiles();
        for (int i = 0; i < files.length; i++) {
            String s = files[i];
            String className = s.substring(0, s.length() - 5).replace(File.separatorChar, '.');
            suite.addTestSuite(Class.forName(className));
        }
        suite.setName("Micro Tests (" + suite.countTestCases() + " tests)");
        return suite;
    }

}
