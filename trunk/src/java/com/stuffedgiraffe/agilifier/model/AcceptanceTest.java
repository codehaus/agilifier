package com.stuffedgiraffe.agilifier.model;

import com.stuffedgiraffe.agilifier.runner.Runner;
import com.stuffedgiraffe.agilifier.util.CamelUtils;

import java.io.File;

public class AcceptanceTest implements AcceptanceTestOrAcceptanceTestContainer {
    private String name;
    private boolean passed;
    private File testFile;
    private File resultFile;
    private Runner runner;

    public AcceptanceTest(File testFile, File resultFile, Runner runner) {
        this.testFile = testFile;
        this.resultFile = resultFile;
        this.runner = runner;
        String filename = testFile.getName();
        name = filename.substring(0, filename.lastIndexOf("."));
    }


    public File getTestFile() {
        return testFile;
    }

    public String getName() {
        return name;
    }

    public boolean isTest() {
        return true;
    }

    public String getDescription() {
        return CamelUtils.uncamel(name);
    }

    public boolean getPassed() {
        return passed;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }

    public File getResultFile() {
        return resultFile;
    }

    @SuppressWarnings({"RedundantIfStatement"})
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AcceptanceTest)) {
            return false;
        }

        final AcceptanceTest acceptanceTest = (AcceptanceTest) o;

        if (passed != acceptanceTest.passed) {
            return false;
        }
        if (name != null ? !name.equals(acceptanceTest.name) : acceptanceTest.name != null) {
            return false;
        }
        if (resultFile != null ? !resultFile.equals(acceptanceTest.resultFile) : acceptanceTest.resultFile != null) {
            return false;
        }
        if (testFile != null ? !testFile.equals(acceptanceTest.testFile) : acceptanceTest.testFile != null) {
            return false;
        }

        return true;
    }

    public int hashCode() {
        int result;
        result = (name != null ? name.hashCode() : 0);
        result = 29 * result + (passed ? 1 : 0);
        result = 29 * result + (testFile != null ? testFile.hashCode() : 0);
        result = 29 * result + (resultFile != null ? resultFile.hashCode() : 0);
        return result;
    }


    public String toString() {
        return "AcceptanceTest{" +
                "name='" + name + "'" +
                ", testFile=" + testFile +
                ", resultFile=" + resultFile +
                ", passed=" + passed +
                "}";
    }

    public Runner getRunner() {
        return runner;
    }
}

