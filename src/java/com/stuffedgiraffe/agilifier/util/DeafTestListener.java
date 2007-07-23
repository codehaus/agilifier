package com.stuffedgiraffe.agilifier.util;

import junit.framework.AssertionFailedError;
import junit.framework.Test;
import junit.framework.TestListener;

public class DeafTestListener implements TestListener {
    public void addError(Test test, Throwable throwable) {

    }

    public void addFailure(Test test, AssertionFailedError assertionFailedError) {
    }

    public void endTest(Test test) {

    }

    public void startTest(Test test) {

    }
}
