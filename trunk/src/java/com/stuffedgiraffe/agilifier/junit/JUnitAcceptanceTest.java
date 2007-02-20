package com.stuffedgiraffe.agilifier.junit;

import com.stuffedgiraffe.agilifier.model.AcceptanceTest;
import com.stuffedgiraffe.agilifier.runner.Runner;
import com.stuffedgiraffe.agilifier.util.Agilifier;
import junit.framework.TestCase;
import junit.framework.TestListener;

public class JUnitAcceptanceTest extends TestCase {
    private AcceptanceTest test;
    private Runner runner;
    private TestListener testListener;

    public JUnitAcceptanceTest(AcceptanceTest test, TestListener testListener) {
        super("testExecuteAcceptanceTest");
        this.test = test;
        this.testListener = testListener;
        runner = test.getRunner();
    }

    public int countTestCases() {
        return 1;
    }

    public void testExecuteAcceptanceTest() {
        testListener.startTest(this);
        String failure;
        if (Agilifier.treatExistingResultFileAsSuccess() && test.getResultFile().exists()) {
            failure = null;
        } else {
            failure = runner.runTest(test);
        }

        boolean passed = (failure == null);
        test.setPassed(passed);

        testListener.endTest(this);

        if (!passed) {
            fail(failure);
        }
    }

    public String getName() {
        return test.getDescription();
    }

    public AcceptanceTest getAcceptanceTest() {
        return test;
    }
}
