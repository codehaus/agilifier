package com.stuffedgiraffe.agilifier.model;

import com.stuffedgiraffe.agilifier.util.CamelUtils;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserStory {
    private Map<File, AcceptanceTest> tests = new HashMap<File, AcceptanceTest>();
    private String name;
    private List<String> storyText;

    public UserStory(String userStory) {
        this.name = userStory;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return CamelUtils.uncamel(name);
    }

    public List getStoryText() {
        return storyText;
    }

    public void setStoryText(List<String> storyText) {
        this.storyText = storyText;
    }

    public void addTest(AcceptanceTest test) {
        tests.put(test.getTestFile(), test);
    }

    public Collection<AcceptanceTest> getAllTests() {
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
