package com.stuffedgiraffe.agilifier.runner;

import junit.framework.TestCase;

import java.io.FilenameFilter;

public class ExactorRunnerTest extends TestCase {

    protected void setUp() throws Exception {
        super.setUp();
    }

    public void testFilenameFilterOnlyMatchesFilesWithExactorExtension() throws Exception {
        ExactorRunner runner = new ExactorRunner();
        FilenameFilter fileFilter = runner.getFileFilter();
        assertTrue(fileFilter.accept(null, "someTest.act"));
        assertTrue(fileFilter.accept(null, "some-Test.act"));
        assertTrue(fileFilter.accept(null, "someOtherTest.act"));
        assertFalse(fileFilter.accept(null, "someTest.ACT"));
        assertFalse(fileFilter.accept(null, "someTest.html"));
    }

    public void testFilenameFilterDoesNotMatchTemporaryFiles() throws Exception {
        ExactorRunner runner = new ExactorRunner();
        FilenameFilter fileFilter = runner.getFileFilter();
        assertTrue(fileFilter.accept(null, "some~Test.act"));
        assertFalse(fileFilter.accept(null, "~someTest.act"));
    }


}
