package com.stuffedgiraffe.agilifier.main;

import com.stuffedgiraffe.agilifier.builder.UserStoriesSuiteBuilder;
import com.stuffedgiraffe.agilifier.junit.JUnitSuiteBuilder;
import com.stuffedgiraffe.agilifier.model.Module;
import com.stuffedgiraffe.agilifier.publisher.FreemarkerFileGenerator;
import com.stuffedgiraffe.agilifier.publisher.HtmlPublisher;
import com.stuffedgiraffe.agilifier.publisher.Publisher;
import junit.framework.TestFailure;
import junit.framework.TestListener;
import junit.framework.TestResult;
import junit.framework.TestSuite;
import org.apache.tools.ant.DirectoryScanner;

import java.io.*;
import java.util.Enumeration;

public class GenerateUserStorySite {

    public static void generateModuleSite(String moduleName, FileContext fileContext, TestListener testListener) throws Exception {
        TestSuite testSuite = suite(moduleName, fileContext, testListener);
        TestResult testResult = new TestResult();
        testSuite.run(testResult);
        printErrors(testResult);
    }

    static TestSuite suite(String moduleName, FileContext fileContext, TestListener testListener) throws Exception {
        try {
            createResultsArea(fileContext);
            Publisher publisher = new HtmlPublisher(new FreemarkerFileGenerator());
            JUnitSuiteBuilder testSuiteBuilder = new JUnitSuiteBuilder(publisher, testListener);
            Module module = UserStoriesSuiteBuilder.buildModule(fileContext, moduleName);
            return testSuiteBuilder.buildJUnitSuite(module, module.getFileContext());
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    static void createResultsArea(FileContext fileContext) throws FileNotFoundException {
        fileContext.getResultsRootDir().mkdirs();
        fileContext.getResultsRootDir().mkdir();

        DirectoryScanner scanner = new DirectoryScanner();
        scanner.setBasedir(fileContext.getTestRootDir());
        String[] excludedFiles = new String[]{"**/story.txt", "**/*.suite", "**/*.html", "**/*.act", "**/.svn/**/*", "**/CVS/**/*"};
        scanner.setExcludes(excludedFiles);
        scanner.scan();
        String[] files = scanner.getIncludedFiles();
        for (String filename : files) {
            copyFileToResultsArea(fileContext, filename);
        }
    }

    private static void copyFileToResultsArea(FileContext fileContext, String filename) throws FileNotFoundException {
        System.out.println("Copying file: " + filename);
        InputStream inputStream;
        File stylesFile = fileContext.getTestFile(filename);
        if (stylesFile.exists()) {
            inputStream = new FileInputStream(stylesFile);
        } else {
            inputStream = GenerateUserStorySite.class.getClassLoader().getResourceAsStream(filename);
        }
        writeStreamToFile(inputStream, fileContext.getResultFile(filename));
    }

    private static void writeStreamToFile(InputStream inputStream, File targetFile) {
        OutputStream outputStream = null;
        try {
            targetFile.getParentFile().mkdirs();
            outputStream = new FileOutputStream(targetFile);
            byte[] bytes = new byte[10240];
            int bytesRead;
            while ((bytesRead = inputStream.read(bytes)) > 0) {
                outputStream.write(bytes, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void printErrors(TestResult testResult) {
        Enumeration errors = testResult.errors();
        while (errors.hasMoreElements()) {
            TestFailure error = (TestFailure) errors.nextElement();
            error.thrownException().printStackTrace();
        }
    }

}
