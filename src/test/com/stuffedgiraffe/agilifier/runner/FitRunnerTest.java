package com.stuffedgiraffe.agilifier.runner;

import com.stuffedgiraffe.agilifier.main.FileContext;
import com.stuffedgiraffe.agilifier.model.AcceptanceTest;
import com.stuffedgiraffe.agilifier.util.MapBuilder;
import fit.FileRunner;
import org.jmock.MockObjectTestCase;
import org.jmock.cglib.Mock;

import java.io.*;

public class FitRunnerTest extends MockObjectTestCase {
    private Mock fileRunnerMock;
    protected File testFile;
    protected FileRunner fileRunner;
    protected FitRunner fitRunner;
    protected AcceptanceTest test;

    protected void setUp() throws Exception {
        super.setUp();

        FileContext fileContext = new FileContext("somefile/path", "someotherfile/path");
        testFile = fileContext.getTestFile("somefile/path/filename.html");
        File expectedResultsFile = fileContext.getResultFile("somefile/path/filename.html");

        fileRunnerMock = new Mock(FileRunner.class);
        fileRunnerMock.expects(once()).method("args").with(eq(new String[]{testFile.getAbsolutePath(), expectedResultsFile.getAbsolutePath()}));
        fileRunnerMock.expects(once()).method("process");

        PrintWriter printWriter = new PrintWriter(new ByteArrayOutputStream());

        fileRunner = (FileRunner) fileRunnerMock.proxy();
        fileRunner.output = printWriter;
        Mock fileRunnerFactoryMock = new Mock(FitRunner.FileRunnerFactory.class);
        fileRunnerFactoryMock.expects(once()).method("createFileRunner").withNoArguments().will(returnValue(fileRunner));
        fitRunner = new FitRunner((FitRunner.FileRunnerFactory) fileRunnerFactoryMock.proxy());

        test = new AcceptanceTest(testFile, expectedResultsFile, fitRunner);
    }

    protected void tearDown() throws Exception {
        try {
            new File("someotherfile/path/somefile/path").delete();
            new File("someotherfile/path/somefile").delete();
            new File("someotherfile/path").delete();
            new File("someotherfile").delete();
        } catch (Exception e) {
            //Ignore - naughty, but what the heck!
        }
    }

    public void testRunTestCallsTheFitFileRunnerAndPassesBackTheResultIfThereAreErrors() throws Exception {
        String summaryCounts = "1 passed, 8 wrong, 9 exceptions";
        fileRunner.fixture.summary = MapBuilder.make("counts", summaryCounts);

        String result = fitRunner.runTest(test);

        assertEquals(summaryCounts, result);
        fileRunnerMock.verify();
    }

    public void testRunTestCallsTheFitFileRunnerAndPassesBackNullIfThereAreNoErrors() throws Exception {
        String summaryCounts = "1 passed, 0 wrong, 0 exceptions";
        fileRunner.fixture.summary = MapBuilder.make("counts", summaryCounts);

        String result = fitRunner.runTest(test);

        assertEquals(null, result);
        fileRunnerMock.verify();
    }

    public void testReturnsUnableToExecuteTestIfSummaryCountIsNull() throws Exception {
        String summaryCounts = null;
        fileRunner.fixture.summary = MapBuilder.make("counts", summaryCounts);

        String result = fitRunner.runTest(test);

        assertEquals("Unable to execute acceptance test", result);
        fileRunnerMock.verify();
    }

    public void testUsesRealFileRunnerByDefault() throws Exception {
        fileRunnerMock.reset();
        fitRunner = new FitRunner();
        assertEquals(FileRunner.class, fitRunner.fileRunnerFactory.createFileRunner().getClass());
    }

    // TODO - Test the correct filename filter is returned.

    public class MockSafePrintWriter extends PrintWriter {
        public MockSafePrintWriter() throws FileNotFoundException {
            super(new ByteArrayOutputStream());
        }
    }
}
