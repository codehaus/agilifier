package com.stuffedgiraffe.agilifier.model;

import com.stuffedgiraffe.agilifier.util.CamelUtils;
import com.stuffedgiraffe.agilifier.util.ListOrderedMap;

import java.util.Collection;
import java.util.Map;

public class UserStorySuite {
    private String name;
    private Module module;
    private Map<String, UserStory> userStories = new ListOrderedMap<String, UserStory>();

    public UserStorySuite(String suiteName, Module module) {
        this.name = suiteName;
        this.module = module;
    }

    public Module getModule() {
        return module;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return CamelUtils.uncamel(name);
    }

    public void addStory(UserStory userStory) {
        userStories.put(userStory.getName(), userStory);
    }

    public Collection<UserStory> getStories() {
        return userStories.values();
    }

    public int getPassedCount() {
        int passed = 0;
        for (UserStory userStorySummary : getStories()) {
            passed += userStorySummary.getPassedCount();
        }
        return passed;
    }

    public int getFailedCount() {
        int failed = 0;
        for (UserStory userStorySummary : getStories()) {
            failed += userStorySummary.getFailedCount();
        }
        return failed;
    }

    public int getFailedPercent() {
        int passed = getPassedCount();
        int failed = getFailedCount();
        if (passed + failed == 0) {
            return 0;
        }
        return 100 * failed / (passed + failed);
    }

    public int getPassedPercent() {
        int passed = getPassedCount();
        int failed = getFailedCount();
        if (passed + failed == 0) {
            return 0;
        }
        return 100 * passed / (passed + failed);
    }


}
