package com.stuffedgiraffe.agilifier.runner;

import com.stuffedgiraffe.agilifier.model.AcceptanceTest;
import fit.FileRunner;

import java.io.File;
import java.io.FilenameFilter;

public class FitRunner implements Runner {

    private static final FilenameFilter HTML_FILE_FILTER = new FilenameFilter() {
        public boolean accept(File dir, String name) {
            return name.endsWith(".html") && !name.startsWith("~");
        }
    };

    interface FileRunnerFactory {
        FileRunner createFileRunner();
    }

    protected FileRunnerFactory fileRunnerFactory;

    public FitRunner() {
        fileRunnerFactory = new FileRunnerFactory() {
            public FileRunner createFileRunner() {
                return new FileRunner();
            }
        };
    }

    public FitRunner(FileRunnerFactory fileRunnerFactory) {
        this.fileRunnerFactory = fileRunnerFactory;
    }

    public String runTest(AcceptanceTest test) {
        File resultFile = test.getResultFile();
        resultFile.getParentFile().mkdirs();

        FileRunner fileRunner = fileRunnerFactory.createFileRunner();

        fileRunner.args(new String[]{test.getTestFile().getAbsolutePath(), resultFile.getAbsolutePath()});
        fileRunner.process();
        fileRunner.output.flush();
        fileRunner.output.close();

        String failure = null;
        String counts = (String) fileRunner.fixture.summary.get("counts");

        if (counts == null) {
            failure = "Unable to execute acceptance test";
        } else if (failed(counts)) {
            failure = counts;
        }
        return failure;
    }

    public FilenameFilter getFileFilter() {
        return HTML_FILE_FILTER;
    }

    private static boolean failed(String counts) {
        return (counts.indexOf(" 0 wrong") == -1) || (counts.indexOf(" 0 exceptions") == -1);
    }


}
