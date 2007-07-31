package com.stuffedgiraffe.agilifier.model;

import com.stuffedgiraffe.agilifier.main.FileContext;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class Module implements AcceptanceTestContainer {
    private List<UserStorySuite> suites = new LinkedList<UserStorySuite>();
    private String name;
    private FileContext fileContext;

    public Module(String name, FileContext fileContext) {
        this.name = name;
        this.fileContext = fileContext;
    }

    public void addUserStorySuite(UserStorySuite suite) {
        suites.add(suite);
    }

    public String getName() {
        return name;
    }

    public boolean isTest() {
        return false;
    }

    public List<UserStorySuite> getUserStorySuites() {
        return suites;
    }

    public FileContext getFileContext() {
        return fileContext;
    }


    public String getDescription() {
        return name;
    }

    public int getPassedCount() {
        throw new UnsupportedOperationException();
    }

    public int getFailedCount() {
        throw new UnsupportedOperationException();
    }

    public int getFailedPercent() {
        throw new UnsupportedOperationException();
    }

    public int getPassedPercent() {
        throw new UnsupportedOperationException();
    }

    public String getPathToRoot() {
        return ".";
    }

    public String getPath() {
        return ".";
    }

    public void addChild(AcceptanceTestOrAcceptanceTestContainer child) {
        throw new UnsupportedOperationException();
    }

    public AcceptanceTestContainer getParent() {
        throw new UnsupportedOperationException();
    }

    public Collection<AcceptanceTestOrAcceptanceTestContainer> getChildren() {
        Collection<AcceptanceTestOrAcceptanceTestContainer> results = new ArrayList<AcceptanceTestOrAcceptanceTestContainer>();
        for (UserStorySuite suite : suites) {
            results.add(suite);
        }
        return results;
    }

    public String getText() {
        return "";
    }

    public void setText(String text) {
        throw new UnsupportedOperationException();
    }

}
