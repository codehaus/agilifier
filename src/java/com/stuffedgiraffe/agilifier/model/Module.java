package com.stuffedgiraffe.agilifier.model;

import com.stuffedgiraffe.agilifier.main.FileContext;

import java.util.LinkedList;
import java.util.List;

public class Module {
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

    public List<UserStorySuite> getUserStorySuites() {
        return suites;
    }

    public FileContext getFileContext() {
        return fileContext;
    }
}
