package com.stuffedgiraffe.agilifier.model;

import com.stuffedgiraffe.agilifier.util.Agilifier;

import java.util.*;

public class UserStory {
    private Map tests = new HashMap();
    private String name;
    private List storyText;

    public UserStory(String userStory) {
        this.name = userStory;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return Agilifier.uncamel(name);
    }

    public List getStoryText() {
        return storyText;
    }

    public void setStoryText(List storyText) {
        this.storyText = storyText;
    }

    public void addTest(AcceptanceTest test) {
        tests.put(test.getTestFile(), test);
    }

    public Collection getAllTests() {
        return tests.values();
    }

    public int getPassedPercent() {
        return percentWithResult(true);
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
        for (Iterator iterator = tests.values().iterator(); iterator.hasNext();) {
            AcceptanceTest result = (AcceptanceTest) iterator.next();
            if (result.getPassed() == passed) {
                count++;
            }
        }
        return count;
    }

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
