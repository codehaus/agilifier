package com.stuffedgiraffe.agilifier.model;

import com.stuffedgiraffe.agilifier.util.CamelUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class UserStory implements AcceptanceTestContainer {
    private String name;
    private Map<File, AcceptanceTest> tests = new HashMap<File, AcceptanceTest>();
    private String text = "";
    private AcceptanceTestContainer parent;

    public UserStory(String userStory) {
        this.name = userStory;
    }

    public void setParent(AcceptanceTestContainer parent) {
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public boolean isTest() {
        return false;
    }

    public String getDescription() {
        return CamelUtils.uncamel(name);
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void addTest(AcceptanceTest test) {
        tests.put(test.getTestFile(), test);
    }

    public void addChild(AcceptanceTestOrAcceptanceTestContainer test) {
        AcceptanceTest acceptanceTest = (AcceptanceTest) test;
        tests.put(acceptanceTest.getTestFile(), acceptanceTest);
    }

    public AcceptanceTestContainer getParent() {
        return parent;
    }

    public Collection<AcceptanceTestOrAcceptanceTestContainer> getChildren() {
        ArrayList<AcceptanceTestOrAcceptanceTestContainer> result = new ArrayList<AcceptanceTestOrAcceptanceTestContainer>(tests.values().size());
        for (AcceptanceTest acceptanceTest : tests.values()) {
            result.add(acceptanceTest);
        }
        return result;
    }

    public int getPassedPercent() {
        return percentWithResult(true);
    }

    public String getPathToRoot() {
        return getParent().getPathToRoot() + "/..";
    }

    public String getPath() {
        return getParent().getPath() + "/" + getName();
    }

    public int getFailedPercent() {
        return percentWithResult(false);
    }

    private int percentWithResult(boolean passed) {
        int matchingCount = countTestsWithResult(passed);
        int testCount = tests.size();
        if (testCount == 0) {
            return 0;
        }
        return 100 * matchingCount / testCount;
    }

    public int getPassedCount() {
        return countTestsWithResult(true);
    }

    public int getFailedCount() {
        return countTestsWithResult(false);
    }

    private int countTestsWithResult(boolean passed) {
        int count = 0;
        for (AcceptanceTest result : tests.values()) {
            if (result.getPassed() == passed) {
                count++;
            }
        }
        return count;
    }

    @SuppressWarnings({"RedundantIfStatement"})
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserStory)) {
            return false;
        }
        final UserStory userStory = (UserStory) o;
        if (!name.equals(userStory.name)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        int result;
        result = name.hashCode();
        return result;
    }

}
