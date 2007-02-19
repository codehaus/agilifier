package com.stuffedgiraffe.agilifier.runner;

import com.stuffedgiraffe.agilifier.model.AcceptanceTest;

import java.io.FilenameFilter;

public interface Runner {
    String runTest(AcceptanceTest testFile);

    FilenameFilter getFileFilter();
}
