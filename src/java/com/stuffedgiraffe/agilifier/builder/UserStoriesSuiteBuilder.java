package com.stuffedgiraffe.agilifier.builder;

import com.stuffedgiraffe.agilifier.main.FileContext;
import com.stuffedgiraffe.agilifier.model.AcceptanceTest;
import com.stuffedgiraffe.agilifier.model.Module;
import com.stuffedgiraffe.agilifier.model.UserStory;
import com.stuffedgiraffe.agilifier.model.UserStorySuite;
import com.stuffedgiraffe.agilifier.runner.ExactorRunner;
import com.stuffedgiraffe.agilifier.runner.FitRunner;
import com.stuffedgiraffe.agilifier.runner.Runner;

import java.io.*;
import java.util.*;

public class UserStoriesSuiteBuilder {
    private static final FileFilter DIRECTORY_FILE_FILTER = new FileFilter() {
        public boolean accept(File file) {
            return file.isDirectory() && !file.getName().equals("CVS") && !file.getName().equals(".svn");
        }
    };
    private Runner[] runners;

    public UserStoriesSuiteBuilder(Runner[] runners) {
        this.runners = runners;
    }

    public UserStoriesSuiteBuilder() {
        this(new Runner[]{new FitRunner(), new ExactorRunner()});
    }

    public UserStorySuite buildUserStorySuite(String suiteName, FileContext fileContext, Module module) throws IOException {
        UserStorySuite userStorySuite = new UserStorySuite(suiteName, module);
        String suiteFile = fileContext.getTestRootDir() + File.separator + suiteName + ".suite";
        BufferedReader reader = new BufferedReader(new FileReader(suiteFile));
        String storyName = reader.readLine();
        while (storyName != null) {
            storyName = storyName.trim();
            UserStory userStory = buildUserStory(storyName, fileContext);
            userStorySuite.addStory(userStory);
            storyName = reader.readLine();
        }
        return userStorySuite;
    }

    public UserStorySuite buildAllUserStoriesSuite(FileContext fileContext, Module module) {
        UserStorySuite userStorySuite = new UserStorySuite("All User Stories", module);
        File[] storyDirectories = fileContext.getTestRootDir().listFiles(DIRECTORY_FILE_FILTER);
        for (int i = 0; i < storyDirectories.length; i++) {
            String storyName = storyDirectories[i].getName();
            UserStory userStory = buildUserStory(storyName, fileContext);
            userStorySuite.addStory(userStory);
        }
        return userStorySuite;
    }

    public UserStorySuite buildOtherUserStoriesSuite(UserStorySuite[] userStorySuites, FileContext fileContext, Module module) {
        UserStorySuite allStoriesSuite = buildAllUserStoriesSuite(fileContext, module);
        Set allStories = new HashSet(allStoriesSuite.getStories());
        for (int i = 0; i < userStorySuites.length; i++) {
            UserStorySuite userStorySuite = userStorySuites[i];
            allStories.removeAll(userStorySuite.getStories());
        }
        UserStorySuite otherStoriesSuite = new UserStorySuite("Future User Stories", module);
        for (Iterator iterator = allStories.iterator(); iterator.hasNext();) {
            UserStory userStory = (UserStory) iterator.next();
            otherStoriesSuite.addStory(userStory);
        }
        return otherStoriesSuite;
    }

    public UserStory buildUserStory(String storyName, FileContext fileContext) {
        UserStory userStory = new UserStory(storyName);
        loadStoryText(userStory, fileContext);
        File storyDir = new File(fileContext.getTestRootDir() + File.separator + storyName);
        for (int i = 0; i < runners.length; i++) {
            Runner runner = runners[i];
            File[] testFiles = storyDir.listFiles(runner.getFileFilter());
            if (testFiles == null) {
                throw new IllegalStateException("Unable to find tests for story: " + storyName);
            }
            for (int j = 0; j < testFiles.length; j++) {
                File testFile = testFiles[j];
                AcceptanceTest acceptanceTest = new AcceptanceTest(testFile, fileContext.getResultsFile(testFile), runner);
                userStory.addTest(acceptanceTest);
            }
        }
        return userStory;
    }

    private static void loadStoryText(UserStory userStory, FileContext fileContext) {
        List lines = new LinkedList();
        File storyFile = fileContext.getTestFile(userStory.getName() + File.separator + "story.txt");
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(storyFile));
            String currentLine = reader.readLine();
            while (currentLine != null) {
                lines.add(currentLine);
                currentLine = reader.readLine();
            }
        } catch (FileNotFoundException e) {
            // If the file is not found then just return an empty list
            lines.add("<< No Story Details >>");
        } catch (IOException e) {
            lines.add(e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    // Nothing makes sense here really!
                }
            }
        }
        userStory.setStoryText(lines);
    }

    public static Module buildModule(FileContext fileContext, String name) throws IOException {
        Module module = new Module(name, fileContext);
        UserStoriesSuiteBuilder factory = new UserStoriesSuiteBuilder();
        UserStorySuite completed = factory.buildUserStorySuite("CompletedStories", fileContext, module);
        UserStorySuite current = factory.buildUserStorySuite("CurrentIteration", fileContext, module);
        UserStorySuite others = factory.buildOtherUserStoriesSuite(new UserStorySuite[]{completed, current}, fileContext, module);
        module.addUserStorySuite(completed);
        module.addUserStorySuite(current);
        module.addUserStorySuite(others);
        return module;
    }

}
