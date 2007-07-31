package com.stuffedgiraffe.agilifier.builder;

import com.stuffedgiraffe.agilifier.main.FileContext;
import com.stuffedgiraffe.agilifier.model.*;
import com.stuffedgiraffe.agilifier.runner.ExactorRunner;
import com.stuffedgiraffe.agilifier.runner.FitRunner;
import com.stuffedgiraffe.agilifier.runner.Runner;
import com.stuffedgiraffe.agilifier.util.Agilifier;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

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
            UserStory userStory = buildUserStory(storyName, userStorySuite, fileContext);
            userStorySuite.addChild(userStory);
            storyName = reader.readLine();
        }
        return userStorySuite;
    }

    public UserStorySuite buildAllUserStoriesSuite(FileContext fileContext, Module module) {
        UserStorySuite userStorySuite = new UserStorySuite("All User Stories", module);
        File[] storyDirectories = fileContext.getTestRootDir().listFiles(DIRECTORY_FILE_FILTER);
        for (File storyDirectory : storyDirectories) {
            String storyName = storyDirectory.getName();
            UserStory userStory = buildUserStory(storyName, userStorySuite, fileContext);
            userStorySuite.addChild(userStory);
        }
        return userStorySuite;
    }

    public UserStorySuite buildOtherUserStoriesSuite(UserStorySuite[] userStorySuites, FileContext fileContext, Module module) {
        UserStorySuite allStoriesSuite = buildAllUserStoriesSuite(fileContext, module);
        Set<AcceptanceTestOrAcceptanceTestContainer> allStories = new HashSet<AcceptanceTestOrAcceptanceTestContainer>(allStoriesSuite.getChildren());
        for (UserStorySuite userStorySuite : userStorySuites) {
            allStories.removeAll(userStorySuite.getChildren());
        }
        UserStorySuite otherStoriesSuite = new UserStorySuite("Future User Stories", module);
        for (AcceptanceTestOrAcceptanceTestContainer userStory : allStories) {
            otherStoriesSuite.addChild(userStory);
        }
        return otherStoriesSuite;
    }

    public UserStory buildUserStory(String storyName, UserStorySuite userStorySuite, FileContext fileContext) {
        UserStory userStory = new UserStory(storyName);
        userStory.setParent(userStorySuite);
        loadStoryText(userStory, fileContext);
        File storyDir = new File(fileContext.getTestRootDir() + File.separator + storyName);
        for (Runner runner : runners) {
            File[] testFiles = storyDir.listFiles(runner.getFileFilter());
            if (testFiles == null) {
                throw new IllegalStateException("Unable to find tests for story: " + storyName);
            }
            for (File testFile : testFiles) {
                AcceptanceTest acceptanceTest = new AcceptanceTest(testFile, fileContext.getResultsFile(testFile), runner);
                userStory.addTest(acceptanceTest);
            }
        }
        return userStory;
    }

    private static void loadStoryText(UserStory userStory, FileContext fileContext) {
        StringBuilder text = new StringBuilder();
        File storyFile = fileContext.getTestFile(userStory.getName() + File.separator + "story.txt");
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(storyFile));
            String currentLine = reader.readLine();
            while (currentLine != null) {
                text.append(currentLine).append("\r\n");
                currentLine = reader.readLine();
            }
        } catch (FileNotFoundException e) {
            // If the file is not found then just return an empty list
            text.append("<< No Story Details >>");
        } catch (IOException e) {
            text.append(Agilifier.getStackTrace(e));
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    // Nothing makes sense here really!
                }
            }
        }
        userStory.setText(text.toString());
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
