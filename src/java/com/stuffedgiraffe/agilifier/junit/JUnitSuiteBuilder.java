package com.stuffedgiraffe.agilifier.junit;

import com.stuffedgiraffe.agilifier.main.FileContext;
import com.stuffedgiraffe.agilifier.model.AcceptanceTest;
import com.stuffedgiraffe.agilifier.model.AcceptanceTestContainer;
import com.stuffedgiraffe.agilifier.model.AcceptanceTestOrAcceptanceTestContainer;
import com.stuffedgiraffe.agilifier.publisher.Publisher;
import junit.framework.TestListener;
import junit.framework.TestResult;
import junit.framework.TestSuite;

public class JUnitSuiteBuilder {
    private Publisher publisher;
    private TestListener testListener;

    public JUnitSuiteBuilder(Publisher publisher, TestListener testListener) {
        this.publisher = publisher;
        this.testListener = testListener;
    }

    public TestSuite buildJUnitSuite(final AcceptanceTestContainer userStorySuite, final FileContext fileContextParam) {
        final FileContext fileContext = new FileContext(fileContextParam, userStorySuite.getName());
        TestSuite suite = new TestSuite() {
            public void run(TestResult testResult) {
                super.run(testResult);
                publisher.generateSummary(userStorySuite, fileContext);
            }
        };
        for (AcceptanceTestOrAcceptanceTestContainer userStory : userStorySuite.getChildren()) {
            if (userStory instanceof AcceptanceTest) {
                JUnitAcceptanceTest junitTest = new JUnitAcceptanceTest((AcceptanceTest) userStory, testListener);
                suite.addTest(junitTest);
            } else {
                suite.addTest(buildJUnitSuite((AcceptanceTestContainer) userStory, fileContext));
            }
        }
        int storyCount = userStorySuite.getChildren().size();
        suite.setName(userStorySuite.getDescription() + " (" + getNumericDescription(storyCount, "item", "items") + ")");
        return suite;
    }

    private String getNumericDescription(int count, String singular, String plural) {
        return count + " " + (count == 1 ? singular : plural);
    }

}
