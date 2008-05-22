package com.stuffedgiraffe.agilifier.main;

import com.stuffedgiraffe.agilifier.util.Agilifier;

import java.io.File;
import java.io.IOException;

public class FileContext {
    private File testRootDir;
    private File resultsRootDir;

    public FileContext(String testRootDir, String resultsRootDir) {
        this.testRootDir = new File(testRootDir);
        this.resultsRootDir = new File(resultsRootDir);
    }

    public File getTestRootDir() {
        return testRootDir;
    }

    public File getResultsRootDir() {
        return resultsRootDir;
    }

    public File getResultFile(String relativePath) {
        return new File(getResultsRootDir() + File.separator + relativePath);
    }

    public File getTestFile(String relativePath) {
        return new File(getTestRootDir() + File.separator + relativePath);
    }

    public File getResultsFile(File testFile) {
        String testRootPath = getTestRootDir().getAbsolutePath();
        String testFilePath = testFile.getAbsolutePath();
        String resultsRootPath = getResultsRootDir().getAbsolutePath();
        String resultFilename = getResultsFilename(testFilePath, testRootPath, resultsRootPath);
        resultFilename = resultFilename.substring(0, resultFilename.lastIndexOf('.')) + ".html";
        return new File(resultFilename);
    }

    protected static String getResultsFilename(String testFilePath, String testRootPath, String resultsRootPath) {
        String pattern = testRootPath.replaceAll("\\\\", "\\\\\\\\");
        String replacement = resultsRootPath.replaceAll("\\\\", "\\\\\\\\");
        return testFilePath.replaceAll(pattern, replacement);
    }

    public static final String RESULTS_ROOT_KEY = "agilifier.results.root";
    public static final String TEST_ROOT_KEY = "agilifier.test.root";

    public static FileContext createFromPropertiesFile() throws IOException {
        String testRoot = Agilifier.getProperty(TEST_ROOT_KEY, "UNSPECIFIED");
        String resultsRoot = Agilifier.getProperty(RESULTS_ROOT_KEY, "UNSPECIFIED");
        return new FileContext(testRoot, resultsRoot);
    }

}
