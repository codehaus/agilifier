package com.stuffedgiraffe.agilifier.runner;

import com.stuffedgiraffe.agilifier.model.AcceptanceTest;

import java.io.File;
import java.io.FilenameFilter;

public class ExactorRunner implements Runner {
    private static final FilenameFilter FILE_FILTER = new FilenameFilter() {
        public boolean accept(File dir, String name) {
            return name.endsWith(".act") && !name.startsWith("~");
        }
    };
    private ExactorRunnerBackEnd exactorBackEnd;

    public String runTest(AcceptanceTest testFile) {
        return getExactorGateway().runTest(testFile);
    }

    private ExactorRunnerBackEnd getExactorGateway() {
        if (exactorBackEnd == null) {
            exactorBackEnd = new ExactorRunnerBackEnd();
        }
        return exactorBackEnd;
    }

    public FilenameFilter getFileFilter() {
        return FILE_FILTER;
    }

}
