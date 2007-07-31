package com.stuffedgiraffe.agilifier.model;

import com.stuffedgiraffe.agilifier.util.CamelUtils;
import com.stuffedgiraffe.agilifier.util.ListOrderedMap;

import java.util.Collection;
import java.util.Map;

public class UserStorySuite implements AcceptanceTestContainer {
    private String name;
    private Map<String, AcceptanceTestOrAcceptanceTestContainer> userStories = new ListOrderedMap<String, AcceptanceTestOrAcceptanceTestContainer>();
    private AcceptanceTestContainer module;
    private String text = "";

    public UserStorySuite(String suiteName, AcceptanceTestContainer module) {
        this.name = suiteName;
        this.module = module;
    }

    public AcceptanceTestContainer getParent() {
        return module;
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

    public void addChild(AcceptanceTestOrAcceptanceTestContainer userStory) {
        userStories.put(userStory.getName(), userStory);
    }

    public Collection<AcceptanceTestOrAcceptanceTestContainer> getChildren() {
        return userStories.values();
    }

    public int getPassedCount() {
        int passed = 0;
        for (AcceptanceTestOrAcceptanceTestContainer container : getChildren()) {
            AcceptanceTestContainer userStorySummary = (AcceptanceTestContainer) container;
            passed += userStorySummary.getPassedCount();
        }
        return passed;
    }

    public int getFailedCount() {
        int failed = 0;
        for (AcceptanceTestOrAcceptanceTestContainer container : getChildren()) {
            AcceptanceTestContainer userStorySummary = (AcceptanceTestContainer) container;
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

    public String getPath() {
        String path = getParent().getPath() + "/" + getName();
        return path;
    }

    public String getPathToRoot() {
        String path = getParent().getPathToRoot() + "/..";
        return path;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
