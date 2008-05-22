package com.stuffedgiraffe.agilifier.junit;

import com.stuffedgiraffe.agilifier.main.FileContext;
import com.stuffedgiraffe.agilifier.model.AcceptanceTest;
import com.stuffedgiraffe.agilifier.model.Module;
import com.stuffedgiraffe.agilifier.model.UserStory;
import com.stuffedgiraffe.agilifier.model.UserStorySuite;
import com.stuffedgiraffe.agilifier.publisher.Publisher;
import junit.framework.Test;
import junit.framework.TestListener;
import junit.framework.TestResult;
import junit.framework.TestSuite;

import java.util.Iterator;
import java.util.List;

public class JUnitSuiteBuilder {
    private Publisher publisher;
    private TestListener testListener;

    public JUnitSuiteBuilder(Publisher publisher, TestListener testListener) {
        this.publisher = publisher;
        this.testListener = testListener;
    }

    public TestSuite buildJUnitSuite(final Module module) {
        TestSuite moduleSuite = new TestSuite(module.getName()) {
            public void run(TestResult testResult) {
                super.run(testResult);
                publisher.generateModuleSummary(module);
            }
        };
        List userStorySuites = module.getUserStorySuites();
        for (int i = 0; i < userStorySuites.size(); i++) {
            UserStorySuite userStorySuite = (UserStorySuite) userStorySuites.get(i);
            moduleSuite.addTest(buildJUnitSuite(userStorySuite, module.getFileContext()));
        }
        return moduleSuite;
    }

    public TestSuite buildJUnitSuite(final UserStorySuite userStorySuite, final FileContext fileContext) {
        TestSuite suite = new TestSuite() {
            public void run(TestResult testResult) {
                super.run(testResult);
                publisher.generateUserStorySuiteSummary(userStorySuite, fileContext);
            }
        };
        for (Iterator iterator = userStorySuite.getStories().iterator(); iterator.hasNext();) {
            UserStory userStory = (UserStory) iterator.next();
            suite.addTest(buildJUnitSuite(userStory, fileContext));
        }
        int storyCount = userStorySuite.getStories().size();
        suite.setName(userStorySuite.getDescription() + " (" + getNumericDescription(storyCount, "story", "stories") + ")");
        return suite;
    }

    public Test buildJUnitSuite(final UserStory userStory, final FileContext fileContext) {
        TestSuite suite = new TestSuite() {
            public void run(TestResult testResult) {
                super.run(testResult);
                publisher.generateUserStorySummary(userStory, fileContext);
            }
        };
        for (Iterator iterator = userStory.getAllTests().iterator(); iterator.hasNext();) {
            AcceptanceTest test = (AcceptanceTest) iterator.next();
            JUnitAcceptanceTest junitTest = new JUnitAcceptanceTest(test, testListener);
            suite.addTest(junitTest);
        }
        int testCount = userStory.getAllTests().size();
        suite.setName(userStory.getDescription() + " (" + getNumericDescription(testCount, "test", "tests") + ")");
        return suite;
    }

    private String getNumericDescription(int count, String singular, String plural) {
        return count + " " + (count == 1 ? singular : plural);
    }

}
